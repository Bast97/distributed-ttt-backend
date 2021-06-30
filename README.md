# distributed-ttt-backend

## Matchmaker
Run with ```mvn spring-boot:run``` and open ```localhost:8080```.

## Game logic
Run with ```mvn compile``` and ```mvn exec:java -Dexec.mainClass=de.dttt.WebSocketServer```, then also run frontend with ```ng serve```, open ```localhost:4200``` and connect to a Websocket like ```ws://localhost:8765/game/{gameID}```.
