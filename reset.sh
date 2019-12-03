#!/bin/bash

stop-yarn.sh
stop-dfs.sh

rm -r /usr/local/hadoop
rm -r /usr/local/spark
rm -r /root/.ssh
rm -r /hadooptmp
