Spring Boot application that provides serveral endpoints to do a quiz matching quotes to their authors. The current DB is in-memory, so there is no data retention between runs.

Things on the roadmap, but haven't been done (yet) due to time constraints are:
  -Adding unit tests
  -Communicating with ChatGPT (or self hosted LLM) to provide hints with the questions
  -Improving the exception handling, it's a bit rough now
  -Improving the logging, it's a bit rough now

The game is for the player to match the three quotes to their author given by the http://localhost:8080/question/{playerId) endpoint. The order in which the quotes are returned to the http://localhost:8080/question/answer doesn't matter as long as all quotes are matched with their author.

The following endpoints are available:
http://localhost:8080/cachedQuotes/all ---GET Returns all quotes scraped and in the database
http://localhost:8080/cachedQuotes/random --GET Returns a random quote
http://localhost:8080/player/{playerId} --GET returns the player corresponding to {playerId}
http://localhost:8080/question/{playerId) --GET generates and returns a question for player corresponding to {playerId}
http://localhost:8080/highscore --GET returns the current sorted list of highscores

http://localhost:8080/player/new --POST create a new player using a json in the format below. Returns a JSON object of the player database object
{"name":"Player Name"}

http://localhost:8080/question/answer --POST attempt to answer the last question the player received in the format below. Returns a JSON object of the player database object
{"playerId":"1", "quotes":[{"quote": "quote1","author": "author1"},{"quote": "quote2","author": "quote2"},{"quote": "quote3","author": "quote3"}]}
