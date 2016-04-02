SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start %JavaHome%java -jar ./jars/temp-controller.jar  
Start %JavaHome%java -jar ./jars/humidity-controller.jar  
start %JavaHome%java -jar ./jars/intrusion-controller.jar  
start %JavaHome%java -jar ./jars/temp-sensor.jar  
Start %JavaHome%java -jar ./jars/humidity-sensor.jar 
Start %JavaHome%java -jar ./jars/intrusion-sensor.jar 