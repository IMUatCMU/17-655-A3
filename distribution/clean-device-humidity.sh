#!/bin/bash

pkill -f 'java -jar (.*)humidity-controller.jar'
pkill -f 'java -jar (.*)humidity-sensor.jar'