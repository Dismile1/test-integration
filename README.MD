Feel free to complete this test task, override or change everything

For start app: 
1. Run mvn clean install
2. Run mvn spring-boot:run
3. Send "Register User" request
4. Use other requests to explore app.

To get use of application please import Postman Collection with prepared requests (TestTask.postman_collection.json).

P.S. Authorization isn't finished and hardcoded, but the purpose is to show how is it used.

Use basic authorization with following credentials:
myTest@email.com
pass

You can use following test data:
Some movies which are in range of 2017-02-01 - 2017-02-28:
263115 (Logan)
321612 (Beauty and the Beast)

Some actors which played in movies in range of 2017-02-01 - 2017-02-28:
6968 (Hugh Jackman) - movie "Logan"
5530 (James McAvoy) - movie "Split"

Note that:
Even though on the API page (https://developers.themoviedb.org/3/discover/movie-discover) there is a description for used parameter (with_cast): "A comma separated list of person ID's. Only include movies that have one of the ID's added as an actor." it doesn't work correctly so that if you add both Hugh Jackman and James McAvoy to favourite actors you will have result 0 movies instead of 2 Logan and Split.
