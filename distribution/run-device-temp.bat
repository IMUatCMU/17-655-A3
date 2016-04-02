SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\

start %JavaHome%java.exe -jar ./jars/temp-controller.jar 
start %JavaHome%java.exe -jar ./jars/temp-sensor.jar 