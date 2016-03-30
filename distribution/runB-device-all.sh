#!/bin/bash

./jre/osx/Contents/Home/bin/java -jar ./jars/temp-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/humidity-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/intrusion-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/fire-alarm-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/sprinkler-controller.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/temp-sensor.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/humidity-sensor.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/intrusion-sensor.jar &
./jre/osx/Contents/Home/bin/java -jar ./jars/fire-sensor.jar &