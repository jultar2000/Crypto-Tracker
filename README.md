# Crypto Tracker App

## Overview
Project created with the view of having an account on an app that gives the capability of tracking cryptocurrencies. \
The project uses the external CoinGecko API to get actual prices of cryptocurrencies. \
The application uses Spring Security to implement the register-login concept, with an email verifying system along with JWT. 

## Technologies
* Java
* Spring Boot (+Spring Security)
* PostgreSQL
* JavaScript
* HTML
* CSS
* Docker

## Run
**Unfortunately, this option is not available for now, due to the problem with deploying an app into the .jar format.** 

To run the application, make sure you have Docker installed on your device.
Next, go to the directory with the docker-compose.yml file and then run:

    docker-compose up -d

After all containers are started, you will be able to access fronted of the app by using:

    localhost:8085

Also, if you want to access the backend, you can do it by referring to the:

    localhost:8084

To shut down the app, run:

    docker-compose down


