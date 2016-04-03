SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\

start /b %JavaHome%java.exe -jar ./jars/intrusion-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/intrusion-sensor.jar 