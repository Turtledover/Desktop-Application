IP=$(ifconfig en0 | grep inet | awk '$1=="inet" {print $2}')
xhost $IP
docker run --rm -e DISPLAY=$IP:0 -p 8091:8042 -v /tmp/.X11-unix:/tmp/.X11-unix -it --network="distributedmarket_static-network" docker-test /bin/bash
