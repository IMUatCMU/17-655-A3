SETLOCAL ENABLEEXTENSIONS
SET JavaHome = %~dp0jre\Windows\bin\
Echo %JavaHome%java.exe
start %JavaHome%java.exe -jar ./jars/message.jar 
start %JavaHome%java.exe -jar ./jars/systemA.jar

