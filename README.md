# rickandmorty

rickandmory is a wrapper around the rickandmortyapi.com which searchs for a character of the Rick and Morty universe.

In this implementation, an **in-memory database** is used to store all episodes and characters at startup so that they can get later queried at maximum speed. This way, the original Rick and Morty API will be loaded just at startup (I will show how this may not be a problem later).

## Package structure

The package structure of this implementation is the one indicated in Spring Boot documentation, and its as follows:

```
├── main
│   ├── java
│   │   └── dev
│   │       └── bracers
│   │           └── rickandmorty
│   │               ├── character
│   │               │   ├── CharacterController.java
│   │               │   ├── Character.java
│   │               │   ├── CharacterNotFoundException.java
│   │               │   ├── CharacterRepository.java
│   │               │   └── CharacterService.java
│   │               ├── consumer
│   │               │   ├── characters
│   │               │   │   ├── CharactersConsumer.java
│   │               │   │   ├── CharactersInfoJSON.java
│   │               │   │   ├── CharactersJSON.java
│   │               │   │   └── CharactersResultJSON.java
│   │               │   ├── ConsumersConfiguration.java
│   │               │   └── episodes
│   │               │       ├── EpisodesConsumer.java
│   │               │       ├── EpisodesInfoJSON.java
│   │               │       ├── EpisodesJSON.java
│   │               │       └── EpisodesResultJSON.java
│   │               ├── episode
│   │               │   ├── Episode.java
│   │               │   └── EpisodeRepository.java
│   │               └── RickandmortyApplication.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates
└── test
    ├── java
    │   └── dev
    │       └── bracers
    │           └── rickandmorty
    │               ├── character
    │               │   ├── CharacterRepositoryTest.java
    │               │   ├── CharacterServiceTest.java
    │               │   └── CharacterTestConfig.java
    │               └── RickandmortyApplicationTests.java
    └── resources
        └── application.properties
```

Packages **dev.bracers.rickandmorty.character** and **dev.bracers.rickandmorty.episode** modularize the application into Characters and Episodes, making the code more simple and scalable, while the **dev.bracers.rickandmorty.consumer** contains the code to consume all the episodes and characters at start-up. The consumers (CharactersConsumer and EpisodesConsumer) implement CommandLineRunner to indicate that they should be run at startup after context has been loaded.

EpisodesJSON (and its components EpisodesInfoJSON and EpisodesResultJSON) and CharactersJSON (and its components CharactersInfoJSON and CharactersResultJSON) are records to store the returned JSON from the original Rick and Morty API.

## How Consumers work

Consumers take the original API endpoints for Characters and Episodes and paginate through them to get all the data we need, adding it to our in-memory db in order: first Episodes entities are retrieved, then Characters entities are.

## How Application works

The application runs at port 9950 by default.

At startup of the application, we already have the data from the original API, so we only need repositories for Episodes and Characters and one Controller (**CharacterController**) to achieve the desired result: query our in-memmory database and get the information with maximum speed.

Notice that the first_appearance field returned is based on the date of the first Episode for the Character we are querying.

An empty list and 404 response is returned if no Characters are found.

## Endpoint

The application only has one endpoint:

```
/api/v1/search-character-appearance?name={a_name}
```

This endpoint returns a JSON object with the character (or characters, as the universe of Rick and Morty is actually a multiverse, so multiple Ricks exists in different planes of existance, for example) containing its name, a list of the episodes its appears on, and the date of first appearance on the series.

## How JUnit 5 tests work

For testing, we first create a pseudo-mock of the startup of the application, adding some arbitrary Episodes and Characters to an in-memmory test database. This is done in **test** -> **dev.bracers.rickandmorty.character.CharacterTestConfig.java**.

After we have the initial values in our repositories, we test the Service and the CharacterRepository itself (at **CharacterServiceTest** and **CharacterRepositoryTest**, respectively.

Run them just as you would with any JUnit test.

## How Postman tests work

Just import the file **postman/rickandmorty_tests.postman_collection.json** of the repository into Postman and run the collection tests. If everything works fine, you will get something like this:

![Tests OK](http://bracers.dev/wp-content/uploads/2023/10/Screenshot-from-2023-10-08-19-20-59.png)

## Docker

You can find the **Dockerfile** for building an image of the application in this repository. The application image is also in my **Docker Hub** repository, which can be found at [https://hub.docker.com/r/rulu158/rickandmorty](https://hub.docker.com/r/rulu158/rickandmorty).

You can run the image at port 9950 as follows:

```
docker pull rulu158/rickandmorty
docker run -p 9950:9950 rulu158/rickandmorty
```

## API Documentation

You can find the API documentation via Swagger at:

```
http://localhost:9950/swagger-ui/index.html
```

## My Deployment

I have deployed the application on my VPS (dev.bracers.dev), and you can call it as follows using my router (developed also in Java and in my Github):

```
https://dev.bracers.dev/app/rickandmorty/path/api/v1/search-character-appearance?name=Super Turkey
```
