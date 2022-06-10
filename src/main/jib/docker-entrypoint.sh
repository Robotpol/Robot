#!/bin/sh

yum install -y wget
wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
yum install -y ./google-chrome-stable_current_*.rpm

# Assumes `java` is on PATH in the base image.
exec java $JAVA_OPTS -cp @/app/jib-classpath-file @/app/jib-main-class-file

