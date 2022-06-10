#!/bin/sh

apt-get install -y firefox

# Assumes `java` is on PATH in the base image.
exec java $JAVA_OPTS -cp @/app/jib-classpath-file @/app/jib-main-class-file

