SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start /b %JavaHome%java.exe -jar ./jars/message.jar
start /b %JavaHome%java.exe -jar ./jars/systemC.jar