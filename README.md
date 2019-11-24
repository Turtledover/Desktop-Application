# Desktop-Application
Desktop Application for users to connect to Distributed Marketplace

How too use: find the executable jar file in /out/artifacts/desk_app_jar/desk_app.jar



## Approaches to run the GUI app in Docker (for Mac users):

* Make sure that you have installed Docker on your machine.

* Go to the root directory of this repo.

* Build the `docker-test` image by running the command `docker build --rm -t docker-test .`.

* First install XQuartz through `brew cask install xquartz`.

* Open the XQuartz through `open -a XQuartz`. A terminal will pop up, just leave it open.

* Open the `Preferences` of XQuartz and click the `Security` tab. Make sure that the `Allow connections from network clients` option is checked. 

* Get the ip address of your machine through the output of `ifconfig` command (the inet value of en0).

* Then run `xhost + <YOUR_IP_ADDRESS>` to add your ip address to the access control list.

* Launch the docker container through `docker run --rm -e DISPLAY=<YOUR_IP_ADDRESS>:0 -p <PORT>:8042 -v /tmp/.X11-unix:/tmp/.X11-unix -it --network="distributedmarket_static-network" docker-test /bin/bash`.

* Add the master server's ip address to `/etc/hosts` file through the command: `echo '<master_server_ip_address>\tmaster\n' >> /etc/hosts`.

* Run `./run.sh` in the container.

* Then you can manipulate the GUI app in docker! Notice that before using this app, you must add the current docker container to the docker network of the existing cluster.

  > For any question, first check the first link in the reference section.


### Reference 

* https://sourabhbajaj.com/blog/2017/02/07/gui-applications-docker-mac/