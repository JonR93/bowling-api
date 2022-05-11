# bowling-api

A web service server that responds to specific API calls that will simulate a bowling game server

## How to run:
From IDE: simply run or debug ~bowling-api\src\main\java\com\springboot\bowling\BowlingApiApplication.java

*Add a player to the system:*

• request: path: http://localhost:8080/player, body: {"name":"<player name>"}

• output: {"status":"ok", "id":"<player id>"}
Where <player id> is a unique string identifying the player.

*Delete a player from the system:*

• request: 
path: http://localhost:8080/player, 
body:{"id":"<player id>"}

• output: {"status":"ok"}

*Start a game for a player:*

• request: path: http://localhost:8080/startGame, body: {"id":"<player id>"}

• output: {"status":"ok"}

*Throw a ball for a player:*

• request: path: http://localhost:8080/throwBall, body: {"id":"<player id>", "frameIndex":<frame index>,
"ballIndex":<ball index>, "score":<score>}

• output: {"status":"ok"}
Where <frame index> is a 0 based integer representing the relevant frame, <ball index> is a 0 based
integer representing the relevant ball, and <score> is an integer score for the number of pins
knocked down by this throw.

*Get a player's current score:*

• request: path: http://localhost:8080/score, query params: id: <player id>

• output: {"status":"ok", "score":<player score>}
Where <player score> is the integer score for the specified user at the requested frame, not
counting any future score calculations. (ie this would be their score if no more balls were thrown)

*Get a player's current scoresheet:*

• request: path: http://localhost:8080/scoresheet, query params: id: <player id>

• output: {"status":"ok", "sheet":<text representation of game scores>}
Where <text representation of game scores> String containing space separated frames, each frame
represented as 1 or 2 characters, like so:

'-' 0 pins knocked down

'1' to '9' Number of pins knocked down

'/' Spare

'X' Strike

*Example:* 8- 7- 53 9/ 9/ X 8- 51 3/ 9-



## Documentation / Testing
Swagger: 

Open http://localhost:8080/swagger-ui/index.html#/ in browser after starting the application. The Swagger dashboard will display all endpoints with some documentation. Endpoints can also be tested from the dashboard. Simply expand one of the controller accordion tabs, select an endpoint, click the 'Try it Out' button, modify the request JSON to your liking, and finally click the 'Execute' button. The response will then be displayed below.

PostMan:

import postman collection file into postman to view and test imports + Documentation. ~\bowling-api\bowling-api.postman_collection.json


## H2 Inmemory Database Console
H2 database console: http://localhost:8080/h2-console

Username: sa

Password: [empty]

Source Url: jdbc:h2:mem:testdb

Configs are located in application.properties
