SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start /b %JavaHome%java.exe -jar ./jars/fire-alarm-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/sprinkler-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/fire-sensor.jar 