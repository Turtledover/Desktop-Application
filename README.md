# Desktop-Application
Desktop Application for users to connect to Distributed Marketplace.
Setup of the distributed marketplace could be found at https://github.com/Turtledover/DistributedMarket
You have to setup the server first before using this client application.

# Compile & Run in IntelliJ
This project uses Maven as project management tools.

Compile in intelliJ
1. Open this directory as a project in IntelliJ
2. In Preferences -> Build, Execution, Deployment -> Build Tools -> Maven -> Runner,
   enable the "Delegate IDE build/run actions to Maven".
3. Click "Build". A jar file named "desk_app-...-with-dependencies.jar" could be find in ./target folder. 
   This is the jar that could be run.

Run in intelliJ
1. Click Run -> Run..., then "Edit Configuration"
2. Click + and add a Java Application
3. Under Configuration -> Path to JAR, choose the jar path from the target folder.
4. Apply and Run.

# Approaches to run the GUI app in Docker (for Mac users):

* Make sure that you have installed Docker on your machine.

* Go to the root directory of this repo.

* Build the `docker-test` image by running the command `docker build --rm -t docker-test .`.

* First install XQuartz through `brew cask install xquartz`.

* Open the XQuartz through `open -a XQuartz`. A terminal will pop up, just leave it open.

* Open the `Preferences` of XQuartz and click the `Security` tab. Make sure that the `Allow connections from network clients` option is checked. Reboot your computer to make it work.

* Get the ip address of your machine through the output of `ifconfig` command (the inet value of en0).

* Then run `xhost + <YOUR_IP_ADDRESS>` to add your ip address to the access control list.

* Before launching the container, make sure that the central server is running.

* Launch the docker container through `docker run --rm -e DISPLAY=<YOUR_IP_ADDRESS>:0 -p <PORT>:8042 -v /tmp/.X11-unix:/tmp/.X11-unix -it --network="distributedmarket_static-network" docker-test /bin/bash`.

* Add the master server's ip address to `/etc/hosts` file through the command: `echo '<master_server_ip_address>\tmaster\n' >> /etc/hosts`.

* Run `./run.sh` in the container.

* Then you can manipulate the GUI app in docker!

  > For any question, first check the first link in the reference section.

## Reference 

* https://sourabhbajaj.com/blog/2017/02/07/gui-applications-docker-mac/

# Use the app in real machines
Current supported OS: Ubuntu 18
Require Java 8

Run
1. Edit "/etc/hosts", add central server entry ([ip address]  master).
2. Clone this repo in the machine.
3. Run `cd target`
4. Run `java -jar desk_app-1.0-SNAPSHOT-jar-with-dependencies.jar`
5. You should see a interface for you to register and login.

Reset
1. Run the `reset.sh` in this folder
2. Remove any additional entries in "/etc/hosts" file

# How to add machines in the app
1. Register and login to the app
2. Go to machines page, fill in the parameters. Sample:
   * Authorized key path - /root/.ssh/authorized_keys
   * Core num - 4
   * Memory - 4096 (in Megabytes)
   * SSH Public Key - (Can keep this empty)
3. Click "Add new machine" button. It may take up to a few minutes as it download hadoop, spark, tensorflow and tensorflowonspark.

# Submit job in the app
Currently, we assume users will upload the code and data to the HDFS manually first.
And also, in order to prevent users to access data of different users in the HDFS, the job is run with submitted user uid.
When uploading the code and data, the code and data has to be uploaded into the directories of the corresponding users.
There is some convenient scripts in https://github.com/Turtledover/DistributedMarket/tree/master/sample to for uploading.

How to use:
1. cd to DistributedMarket/sample 
2. run `./runas.sh [username] "cd $PWD/mnist && ./hdfs_copy_user.sh"` to upload MNIST code and data to HDFS.
3. run `./runas.sh [username] "cd $PWD/cifar10 && ./hdfs_copy_user.sh"` to upload CIFAR10 code and data to HDFS.

## Parameters to submit MNIST and CIFAR10 sample in the app
All parameters here is assumed to be submitted by a user named "test". For other user name, replace "test" in the hdfs path to your own username. 
MNIST Data Convert
(Entry) hdfs:///user/test/mnist/input/code/mnist_data_setup.py
(Archives) hdfs:///user/test/mnist/input/data/mnist.zip#mnist
(Params) --output mnist/output --format csv

MNIST Train:
(Entry) hdfs:///user/test/mnist/input/code/spark/mnist_spark.py
(Libs) hdfs:///user/test/mnist/input/code/spark/mnist_dist.py
(Params) --images mnist/output/train/images --labels mnist/output/train/labels --mode train --model mnist/output/mnist_model

MNIST Test:
(Entry) hdfs:///user/test/mnist/input/code/spark/mnist_spark.py
(Libs) hdfs:///user/test/mnist/input/code/spark/mnist_dist.py
(Params) --images mnist/output/test/images --labels mnist/output/test/labels --mode inference --model mnist/output/mnist_model --output mnist/output/predictions

Cifar10 Train
(Entry) hdfs:///user/test/cifar/input/code/cifar10_train.py
(Libs) hdfs:///user/test/cifar/input/code/cifar10.zip
(Params) --data_dir hdfs://master:9000/user/test/cifar/input/data/ --train_dir hdfs://master:9000/user/test/cifar/output/train --max_steps 1000

Cifar10 Test
(Entry) hdfs:///user/test/cifar/input/code/cifar10_eval.py
(Libs) hdfs:///user/test/cifar/input/code/cifar10.zip
(Params) --data_dir hdfs:///user/test/cifar/input/data/ --checkpoint_dir hdfs:///user/test/cifar/output/train --eval_dir hdfs:///user/test/cifar/output/eval/ --run_once
