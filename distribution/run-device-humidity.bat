SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\

start /b %JavaHome%java.exe -jar ./jars/humidity-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/humidity-sensor.jar 