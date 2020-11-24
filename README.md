# Player type processing module

This repository SpringBoot REST API which can ingest JSON and process the player data according to the type.

## Instructions 

Clone the repository: `git clone https://github.com/ArnabChatterjee/player.git`

## Running the clinical trial bootstrap application 

### Pre-requisite 

* Maven 3
* Java 8 or above
* Zookeeper version 3.6.2
	- Add ZOOKEEPER_HOME = <dir> to the System Variables.
* Kafka 2.6.0

### Building the app

    cd truelogic
    mvn package

### Running the application

	Pre-requisite
		a) backend services
			- zookeeper should be up and running (Run ZooKeeper by opening a new cmd and type --> zkserver.cmd)
			- kafka server should be up and running (for windows run --> kafka-server-start.bat .\config\server.properties)
		b) Kafka topic "novice-players" should be created using the below command
			kafka-topics.bat --create --zookeeper localhost:2181 -replication-factor 1 --partitions 1 --topic novice-players
	Optional
		a) To view the messages posted in Kafka run the below command
			kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic novice-players
		b) To view the data inserted in H2 database
			http://localhost:8081/h2-console/login.do?jsessionid=394046b590d720ccb5604863b59b0576
			Use the URL as jdbc:h2:mem:truelogic and default settings to login
			To view the records in the Database run the below command
				SELECT * FROM PLAYERS_EXPERT 
			
	Running the test cases
	mvn compile install

### Running the application standalone for further testing	
    cd truelogic
    java -jar target/truelogic-api-0.1.0.jar

### Accessing the API
Once the application is running, the following end points should work

http://localhost:8081 - Application health check endpoint

Run the below curl command to returns a list of processed expected data in standalone mode

curl --header "Content-Type: application/json" --request POST --data "{ \"players\": [{\"name\":\"Sub zero\",\"type\":\"expert\"},{\"name\":\"Scorpion\",\"type\":\"novice\"},{\"name\":\"Reptile\",\"type\":\"meh\"}]}" http://localhost:8081/processPlayers
