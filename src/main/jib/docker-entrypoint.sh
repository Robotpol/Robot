#!/bin/sh

yum install -y wget
wget -O /tmp/google-chrome-stable.rpm https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
yum install -y /tmp/google-chrome-stable.rpm

yum install -y unzip
wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/`curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
unzip /tmp/chromedriver.zip chromedriver -d /usr/bin/

# Assumes `java` is on PATH in the base image.
exec java $JAVA_OPTS -cp @/app/jib-classpath-file @/app/jib-main-class-file

