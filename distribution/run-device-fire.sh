#!/bin/bash

./jre/osx/Contents/Home/bin/java -jar ./jars/fire-alarm-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/sprinkler-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/fire-sensor.jar &