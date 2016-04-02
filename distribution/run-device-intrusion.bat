SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\

start %JavaHome%java.exe -jar ./jars/intrusion-controller.jar 
start %JavaHome%java.exe -jar ./jars/intrusion-sensor.jar 