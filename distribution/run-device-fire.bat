SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start %JavaHome%java.exe -jar ./jars/fire-alarm-controller.jar 
start %JavaHome%java.exe -jar ./jars/sprinkler-controller.jar 
start %JavaHome%java.exe -jar ./jars/fire-sensor.jar 