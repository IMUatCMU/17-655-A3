SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
start %JavaHome%java.exe -jar ./jars/message.jar
start %JavaHome%java.exe -jar ./jars/systemC.jar