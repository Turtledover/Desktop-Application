import os
import socket
import subprocess
import argparse
import logging
import tempfile
import xml.etree.ElementTree as ET

log = open("/root/log-add-worker.txt", "a")
def basic_env_setup():
    """
    Install the required packages on the user machine
    :return:
    """
    commands = [
        ['apt-get', 'clean'],
        ['apt-get', 'update'],
        ['apt-get', '-y', 'dist-upgrade'],
        ['apt-get', 'install', 'openjdk-8-jdk', 'openssh-server', 'openssh-client', 'python3-pip', 'zip', '-y'],
        # TODO Check if keys have already existed:
        ['ssh-keygen', '-t', 'rsa', '-N', '', '-f', '/root/.ssh/id_rsa'],
        ['service', 'ssh', 'start'],
        ['pip3', 'install', 'psutil', 'requests'],
    ]
    for command in commands:
        subprocess.Popen(command, stdout=log, stderr=log).wait()
    logging.info("Finish basic_env_setup")


def cluster_setup():
    """
    Set up the environment variables required for the cluster including Hadoop, Spark, Yarn, Tensorflow, and TensorflowOnSpark
    :return:
    """
    scp_commands = [
        ['scp', '-o', 'StrictHostKeyChecking=no', '-r', 'master:/usr/local/hadoop', '/usr/local/hadoop'],
        ['scp', '-o', 'StrictHostKeyChecking=no', '-r', 'master:/usr/local/spark', '/usr/local/spark'],
    ]
    for command in scp_commands:
        subprocess.Popen(command, stdout=log, stderr=log).wait()

    logging.info("Finish scp")

    envs = {
        'PATH': '{0}:/usr/local/hadoop/bin:/usr/local/hadoop/sbin:/usr/local/spark/bin'.format(os.environ['PATH']),
        'HADOOP_HOME': '/usr/local/hadoop',
        'JAVA_HOME': '/usr/lib/jvm/java-8-openjdk-amd64',
        'SPARK_HOME': '/usr/local/spark',
        'PYSPARK_PYTHON': '/usr/bin/python3.6',
        'SPARK_YARN_USER_ENV': 'PYSPARK_PYTHON=/usr/bin/python3.6',
        'LIB_HDFS': '/usr/local/hadoop/lib/native',
        'LIB_JVM': '$JAVA_HOME/jre/lib/amd64/server',
    }
    for key in envs:
        os.environ[key] = envs[key]
    extra_envs = {
        'HADOOP_CONF_DIR': '{0}/etc/hadoop'.format(os.environ['HADOOP_HOME']),
        'LD_LIBRARY_PATH': '{0}:{1}'.format(os.environ['LIB_HDFS'], os.environ['LIB_JVM']),
    }
    for key in extra_envs:
        os.environ[key] = extra_envs[key]

    os.environ['CLASSPATH'] = subprocess.check_output(['hadoop', 'classpath', '--glob']).decode().strip('\n')
    envs['CLASSPATH'] = os.environ['CLASSPATH']
    logging.info("Finish python env setup")

    rm_commands = [
        ['rm', '-rf', os.path.join(os.environ['HADOOP_HOME'], 'data/dataNode/')],
        ['rm', '-rf', os.path.join(os.environ['HADOOP_HOME'], 'logs')],
    ]
    for rm_command in rm_commands:
        subprocess.Popen(command, stdout=log, stderr=log).wait()
    logging.info("Finish remove the logs and old dataNode directory")

    with open('/root/.bashrc', 'a') as f:
        for key in envs:
            f.write('{0}={1}\n'.format(key, envs[key]))
    with open('/root/.bash_profile', 'a') as f:
        for key in envs:
            f.write('{0}={1}\n'.format(key, envs[key]))
    logging.info("Finish bash env setup")


def config_yarn_resources(cpu_cores_limit, memory_limit):
    """
    :param int cpu_cores_limit:
    :param int memory_limit: In MB
    :return:
    """
    # https://docs.python.org/3.7/library/xml.etree.elementtree.html#module-xml.etree.ElementTree
    # begin
    yarn_config_path = os.path.join(os.environ['HADOOP_CONF_DIR'], 'yarn-site.xml')
    yarn_config = ET.parse(yarn_config_path)
    root = yarn_config.getroot()
    # [TBD] Check if the memory & cpu limit values have already existed
    memory_config = ET.Element('property')
    memory_config_name = ET.SubElement(memory_config, 'name')
    memory_config_name.text = 'yarn.nodemanager.resource.memory-mb'
    memory_config_value = ET.SubElement(memory_config, 'value')
    memory_config_value.text = str(memory_limit)
    root.append(memory_config)
    cpu_config = ET.Element('property')
    cpu_config_name = ET.SubElement(cpu_config, 'name')
    cpu_config_name.text = 'yarn.nodemanager.resource.cpu-vcores'
    cpu_config_value = ET.SubElement(cpu_config, 'value')
    cpu_config_value.text = str(cpu_cores_limit)
    root.append(cpu_config)
    yarn_config.write(yarn_config_path)
    logging.info("Finish the yarn config setup")
    hadoop_commands = [
        ['hdfs', '--daemon', 'start', 'datanode'],
        ['yarn', '--daemon', 'start', 'nodemanager'],
    ]
    for command in hadoop_commands:
        subprocess.Popen(command, stdout=log, stderr=log).wait()
    logging.info("Finish the daemon launching")
    # end


def tensorflow_setup():
    commands = [
        ['pip3', 'install', 'tensorflow', 'tensorflowonspark==1.4.4'],
    ]
    for command in commands:
        subprocess.Popen(command, stdout=log, stderr=log).wait()
    logging.info("Finish the tensorflow setup")


def register_machine(core_num, memory_size, time_period, public_key, authorized_key_path, sessionid, csrftoken, master):
    """
    Register the user machine on the existing cluster.
    :param core_num:
    :param memory_size:
    :param time_period:
    :param public_key:
    :param authorized_key_path:
    :return:
    """
    logging.info('Register_machine')
    import psutil
    import requests
    # The ways to access the machine data is cited from
    # https://www.pythoncircle.com/post/535/python-script-9-getting-system-information-in-linux-using-python-script/
    # https://stackoverflow.com/questions/1006289/how-to-find-out-the-number-of-cpus-using-python
    # https://stackoverflow.com/questions/22102999/get-total-physical-memory-in-python/28161352
    # begin
    core_limit = os.cpu_count()
    memory_limit = psutil.virtual_memory().total  # in Bytes
    logging.info('contribute cpu cores {0} with limit {1}'.format(core_num, core_limit))
    logging.info('contribute memory {0} with limit {1}'.format(memory_size, memory_limit))
    assert core_num <= core_limit and core_num >= 1
    assert memory_size*1024 <= memory_limit and memory_size >= 1024

    # https://stackoverflow.com/questions/22567306/python-requests-file-upload
    # https://stackoverflow.com/questions/13567507/passing-csrftoken-with-python-requests
    # https://www.geeksforgeeks.org/display-hostname-ip-address-python/
    # begin
    url = master + '/services/machine/submit/' #'http://192.168.1.12:8000/services/machine/submit/'
    client = requests.session()
#     tf = tempfile.TemporaryFile()
#     tf.write(public_key.encode('utf-8')
    files = {
        'public_key': open('/root/.ssh/id_rsa.pub', 'r'),
    }
    # TODO Get the real ip address (Docker version different from the real machine)
    hostname = socket.gethostname()
    ip_address = socket.gethostbyname(hostname)
    data = {
        'ip_address': ip_address,
        'core_num': core_num,
        'memory_size': memory_size,
        'time_period': time_period,
        'csrfmiddlewaretoken': csrftoken,
    }
    cookies = requests.cookies.RequestsCookieJar()
    cookies.set('sessionid', sessionid)
    cookies.set('csrftoken', csrftoken)
    logging.info('Before submit machine request')
    response = client.post(url, data=data, files=files, headers=dict(Referer=url), cookies=cookies)
    logging.info('After submit machine request')
    public_keys = response.json()['public_keys']
    with open(authorized_key_path, 'a') as f:
        for public_key in public_keys:
            f.write(public_key)
    host_ip_mapping = response.json()['host_ip_mapping']
    with open('/etc/hosts', 'a') as f:
        for host in host_ip_mapping:
            f.write('{0}\t{1}\n'.format(host_ip_mapping[host], host))
    # end
    # end


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Initialize the worker server.')
    parser.add_argument('--authorized-key-path', type=str, help='The path to the authorized keys path.', default='/root/.ssh/authorized_keys')
    parser.add_argument('--cpu-cores', type=int, help='The number of cpu cores to be contributed to the cluster.',
                        required=True)
    parser.add_argument('--memory-size', type=int, help='The memory size to be contributed to the cluster.',
                        required=True)
#     parser.add_argument('--public-key', type=str, help='The public key of the user machine.')
    parser.add_argument('--sessionid', type=str, help='The id of the current user session.',
                        required=True)
    parser.add_argument('--csrftoken', type=str, help='The csrf_token for the purpose of security.',
                        required=True)
    parser.add_argument('--master-url', type=str, help='The url of master server',
                            required=True)
    args = vars(parser.parse_args())
    logging.basicConfig(filename='init_worker.log', level=logging.INFO)

    basic_env_setup()
    register_machine(args['cpu_cores'], args['memory_size'], 10, '/root/.ssh/id_rsa.pub', args['authorized_key_path'],
                     args['sessionid'], args['csrftoken'], args['master_url'])
    cluster_setup()
    config_yarn_resources(args['cpu_cores'], args['memory_size'])
    tensorflow_setup()
    log.close()
