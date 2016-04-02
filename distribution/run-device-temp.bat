SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\

start /b %JavaHome%java.exe -jar ./jars/temp-controller.jar 
start /b %JavaHome%java.exe -jar ./jars/temp-sensor.jar 