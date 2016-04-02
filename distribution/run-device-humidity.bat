SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\

start %JavaHome%java.exe -jar ./jars/humidity-controller.jar 
start %JavaHome%java.exe -jar ./jars/humidity-sensor.jar 