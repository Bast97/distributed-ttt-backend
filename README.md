# distributed-ttt-backend

## Matchmaker
Run with ```mvn spring-boot:run``` and open ```localhost:8080```.

## Gameserver
Run with ```mvn compile``` and ```mvn exec:java -Dexec.mainClass=de.dttt.WebSocketServer```, then also run frontend with ```ng serve```, open ```localhost:4200``` and connect to a Websocket like ```ws://localhost:8765/game/{gameID}```.

## Run containerised

The gameserver container expects a matchmaker container named "matchmaker" on the same docker network running on port 8080. If the matchmaker server is located somewhere else, pass the MATCHMAKER_HOST and MATCHMAKER_PORT environment variables to the container.
To create a docker network named "dttt_net", run  ```docker network create dttt_net```.

Build the Matchmaker image (```docker build -t <tag> .```), run the container and map port 8080 to the desired host port: ```docker run -d -p <port>:8080 --name matchmaker --network dttt_net <tag>```

Build the gameserver image (```docker build -t <tag> .```), run the container and map port 8765 to the desired host port: ```docker run -d -p <port>:8765 --network dttt_net <tag>``` or ```docker run -d -p <port>:8765 -e MATCHMAKER_HOST=<host> -e MATCHMAKER_PORT=<port> <tag>```