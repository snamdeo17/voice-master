# voice-master
This is an assignment for preparing the mastercard interview. This application is based on voice recognition system which will perform action through the voice commands.

How to run the project

Step 1 : Download the repository

$ git clone https://github.com/snamdeo17/voice-master.git

$ cd voice-master

Step 3 : Setup your mysql database

Execute voice-master/data.sql on voiceMaster db - See application.properties for DB configuration

Step 4 : Build the project using maven

$ mvn clean install
Step 5 : run the project

$ java -jar target/voicemaster-0.0.1-SNAPSHOT.jar
Now navigate to http://localhost:8080/

No initial setup required for database queries.

# Deposit after server start
POST http://10.12.6.165:8080/api/wallet/{wallet-id}/account/{account-id}/deposit/{amount}



