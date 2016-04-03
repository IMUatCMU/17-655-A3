SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start /b %JavaHome%java.exe -jar ./jars/temp-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/humidity-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/intrusion-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/fire-alarm-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/sprinkler-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/temp-sensor.jar 
start /b %JavaHome%java.exe -jar ./jars/humidity-sensor.jar 
start /b %JavaHome%java.exe -jar ./jars/intrusion-sensor.jar 
start /b %JavaHome%java.exe -jar ./jars/fire-sensor.jar 