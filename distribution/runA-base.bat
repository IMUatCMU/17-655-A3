SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
Echo %JavaHome%java.exe
start /b %JavaHome%java.exe -jar ./jars/message.jar 
start /b %JavaHome%java.exe -jar ./jars/systemA.jar

