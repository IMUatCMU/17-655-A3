#!/bin/bash

pkill -f 'java -jar (.*)fire-alarm-controller.jar'
pkill -f 'java -jar (.*)sprinkler-controller.jar'
pkill -f 'java -jar (.*)fire-sensor.jar'