SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start /b %JavaHome%java -jar ./jars/temp-controller.jar  
Start /b %JavaHome%java -jar ./jars/humidity-controller.jar  
start /b %JavaHome%java -jar ./jars/intrusion-controller.jar  
start /b %JavaHome%java -jar ./jars/temp-sensor.jar  
Start /b %JavaHome%java -jar ./jars/humidity-sensor.jar 
Start /b %JavaHome%java -jar ./jars/intrusion-sensor.jar 