# 17-655 A3

## Table of Contents

* [1. Description](#description)
	* [1.1 System A](#system-a)
	* [1.2 System B](#system-b)
	* [1.3 System C](#system-c)
* [2. Build Run and Use](#build-run-and-use)
	* [2.1](#build)
	* [2.2](#run)
	* [2.3](#use)
* [3. Message Codes](#message-codes)
* [4. Project Structure](#project-structure)

## 1. Description

This is a guidance to the source code and program as a result of **CMU 17-655 Assignment 3**. In this assignment, we built a distributed museum environment control system. The system adopts a message-oriented architecture. It features a central monitor/console and pluggable sensors and controllers.

As per the requirements of the assignment, we present the features in `System A`, `System B` and `System C`.

### 1.1 System A

System A features:

- Temperature sensor
- Temperature controller
- Humidity sensor
- Humidity controller
- Intrusion sensor
- Intrusion controller
- Central monitor with indicators
- Central console

The temperature sensor samples ambient temperature readings and send it back to the monitor. The monitor would react to those readings based on its temperature upper/lower limit settings. If the temperature is too high or too low, it would turn on the indicator accordingly and signal the temperature controller to turn on or off the chiller and/or heater. (Note in this implementation, the temperature sensor is a software simulation and only creates its readings based on the tempreature controller control signal)

The humidity sensor samples ambient humidity readings and send it back to the monitor. The monitor would react to those readings based on its humidity upper/lower limit settings. If the humidity is too high or too low, it would turn on the indicator accordingly and signal the humidity controller to turn on or off the humidifer and/or the dehumidifer. (Note in this implementation, the humidity sensor is a software simulation and only creates its readings based on the humidity controller control signal)

The intrusion sensor can detect window break in, door break in and motions. It reports its status periodically to the monitor. On a break in event, the monitor will turn on the indicator and the intrusion controller will sound the alarm. In this simulation, the alarm is simulated by a colored indicator and intrusion event is simulated by a command input on the console (more on the commands later).

### 1.2 System B

System B features:

- All features from System A
- Fire sensor
- Fire alarm controller
- Sprinkler controller

The fire sensor reports the fire level periodically to the monitor. When the level is greater than 0, there is a fire. And the monitor will turn on the fire alarm controller and sprinkler upon user approval. If user does not react within 10 seconds, they are automatically turned on. The extinguishing process is simulated by deducting a constant level of 5 from the total fire level on every sprinkler heartbeat. A fire event can be simulated by a command input from the console with a specific fire level between 0 and 50 (more on the commands later).

### 1.3 System C

- All features from System A and System B
- Device status registry and monitor
- Device status query from console

System C will receive device heartbeats to confirm if they are online or offline. Heartbeat is sent to monitor every second. A device is regarded to go offline if no heartbeat is received from it in 30 seconds. Any online and offline event is displayed to the device status monitor. And also user can type in a command to query the currently online and offline devices.

## 2. Build Run and Use

This section describes how to build and execute the system.

### 2.1 Build

Make sure you have [Apache Maven 3.3.3](https://maven.apache.org/docs/3.3.3/release-notes.html) installed. Then execute in the terminal or command prompt:

```shell
git clone https://github.com/davidiamyou/17-655-A3.git
cd 17-655-A3
mvn clean install
```

The artifacts will be placed in the `./distribution/jars` folder. There should be 13 jars:

- fire-alarm-controller.jar
- fire-sensor.jar
- humidity-controller.jar
- humidity-sensor.jar
- intrusion-controller.jar
- intrusion-sensor.jar
- message.jar
- sprinkler-controller.jar
- systemA.jar
- systemB.jar
- systemC.jar
- temp-controller.jar
- temp-sensor.jar

### 2.2 Run

The generated jars require `JRE 1.8` to run. We have included the `JRE` environment for `Windows (x64)` and `OS X` in the `./distribution/jre` folder. The generated jars are all executables, but since System A(B|C) needs different jars to compose its overall functionality, we have included **execution scripts** (for `OS X`) and **exe wrapper** (for `Windows`) to make execution more convienient.

**To run on Windows**

Open the `A3UI.exe`. The application is a UI wrapper for the windows batch scripts that opens the jars in the background. Windows may display security prompts several times. Confirming those prompts has no harm.

To execute a system, execute the system base first, and then all its devices. Alternatively, you can execute only the devices in interest. For example, for system A, click `Run SystemA Base` first, and then click `Run SystemA Device All`, or alternatively click `Run Device Intrusion`.

__It is important to click `CleanAll` after you finish with each system, otherwise conflicting background process may break next execution__

![Windows UI]
(./static/WindowsUI.png)

**To run on OS X**

```shell
cd distribution
chmod +x *sh
./runA-base.sh
#or ./runB-base.sh 
#or ./runC-base.sh
```

This starts the monitor/console and message middleware for System A(B|C), now you have the choice to start individal device pairs (sensor and controller) or start everything. Note that it is not recommended to start everything on a single computer, the Java threads can consume a lot of resources, which affects message throughput. Some controller reaction can be deplayed on a slow computer, which can cause confusion.

To start individal device pair:

```shell
# temperature
./run-device-temp.sh

# humidity
./run-device-humidity.sh

# intrusion
./run-device-intrusion.sh

# fire
./run-device-fire.sh
```

To start everything:

```shell
# system A
./runA-device-all.sh

# system B
./runB-device-all.sh

# system C
./runC-device-all.sh
```

Most of the scripts above starts `Java` on a background process, so `Command+C` will not terminate properly. Please use the provided scripts to clean up after each execution.

```shell
# clean all java process
./clean-all.sh

# clean fire devices
./clean-device-fire.sh

# clean humidity devices
./clean-device-humidity.sh

# clean intrusion devices
./clean-device-intrusion.sh

# clean temperature devices
./clean-device-temp.sh
```

### 2.3 Use

As mentioned before, some of the sensor events are simulated by typing command into the console. The following commands are supported:

**ecs-console> help**

Outputs a list of supported commands and their description

**ecs-console> temp --low %d --high %d**

Configures temperature range (--low integer --high integer)

**ecs-console> humid --low %d --high %d**

Configures humidity range (--low integer --high integer)

**ecs-console> break-in simulate --target [window|door|motion|all]**

Simulate a break in event for one or all of window, door and motion

**ecs-console> break-in reset --target [window|door|motion|all]**

Reset a break in simulation for one or all of window, door and motion

**ecs-console> intrusion-alarm arm --target [window|door|motion|all]**

Arm the intrusion alarms

**ecs-console> intrusion-alarm disarm --target [window|door|motion|all]**

Disarm the intrusion alarms

**ecs-console> fire simulate --level %d**

Simulate a fire event with a fire level between 0 and 50

**ecs-console> fire sprinkler on**

Manually turn on sprinkler. Useful when user cancelled the sprinkler off, but regreted.

**ecs-console> fire sprinkler off**

Manually turn off the sprinkler.

**ecs-console> device-status**

List all the online devices

**ecs-console> x**

Properly exists the program

## 3. Message Codes

Internally, the devices exchanges data, events and control information using a standardized message schema. In addition to what is already described in the assignment handout, the project also included the following message codes:

| Code | Target                             | Message                      | Description               |
| ---- |:----------------------------------:|:----------------------------:|--------------------------:|
| 6    | monitor, intrusion controller      | W1                           | Window intrusion          |
|      |                                    | W0                           | Window status is normal   |
|      |                                    | D1                           | Door intrusion            |
|      |                                    | D1                           | Door status is normal     |
|      |                                    | M0                           | Motion detected           |
|      |                                    | M0                           | Motion status is normal   |
| -6   | intrusion controller 		        | W1                           | Arm window                |
|      |                                    | W0                           | Disarm window			   |
|      |                                    | D1                           | Arm door            	   |
|      |                                    | D0                           | Disarm door               |
|      |                                    | M1                           | Arm motion                |
|      |                                    | M0                           | Disarm motion             |
| 7    | intrusion sensor 			        | W1                           | Simulate window break in  |
|      |                                    | W0                           | Reset window simulation   |
|      |                                    | D1                           | Simulate door break in    |
|      |                                    | D9                           | Reset door simulation     |
|      |                                    | M1                           | Simulate motion detection |
|      |                                    | M0                           | Reset motion simulation   |
| 8    | fire sensor            			| integer[0,50]                | Simulate fire   		   |
| -8   | fire alarm controller            	| F1		                   | Turn on fire alarm   	   |
|      | 									| F0		                   | Turn off fire alarm   	   |
| 9    | monitor							| integer	                   | Report fire level   	   |
| -9   | sprinkler controller				| S1		                   | Turn on sprinkler   	   |
|      | 									| S0		                   | Turn off sprinkler   	   |
| -10  | fire sensor						| integer	                   | Water level from sprinkler|
| 1000 | device monitor						| $deviceId;$description	   | Device registration       |
| 1001 | device monitor						| $deviceId					   | Device heartbeat	       |
| 1002 | device monitor						| $deviceId					   | Device unregistration	   |

## 4. Project Structure

The project is broken down to 17 modules:

- `shared`: shared infrastructure, configurable UI, temperature and humidity devices, utilities
- `message`: message middleware
- `systemA`: System A wrapper
- `temp-sensor`: temperature sensor
- `humidity-sensor`: humidity sensor
- `temp-controller`: temperature controller
- `humidity-controller`: humidity controller
- `intrusion-sensor`: intrusion sensor
- `intrusion-controller`: intrusion controller
- `systemB`: System B wrapper
- `systemA-core`: System A dependencies, extracted so it's easy to include in System B and C without system specific configuration files
- `systemB-core`: System B dependencies, extracted for the same reason
- `fire-sensor`: fire sensor
- `fire-alarm-controller`: fire alarm controller
- `sprinkler-controller`: sprinkler controller
- `systemC-core`: System C dependencies
- `systemC`: System C

Most of these modules is built to be their own executable jars, except for `shared`, `systemA-core`, `systemB-core` and `systemC-core`.