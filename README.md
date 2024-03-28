Before running docker build run mvn clean package in terminal then run docker build --tag wildfly:api . in terminal

Laboration 1
Här ska vi implementera en Restful Web service med hjälp av JAX-RS API och som körs på en
WildFly applikations server version 31.0.0.Final och vi använder Jakarta EE 10.0 specifikationen.
Koden ska publiceras på github och arbetet sker i grupp.
Följande ska implementeras:
• @Entity klass som kan lagras i en databas tabell. Tex, Person, Movie, Meeting…
• Repositorieklass med koppling till databas.
• Implementation för Rest api med CRUD funktioner för entiteten. (Create, Read, Update,
Delete)
• Informationen ska skickas som json mellan klient och server.
• DTO ska användas, vi vill alltså inte serializera och deserializera entity klasser direkt.
• Felhantering och meningsfulla Response koder ska användas.
How to handle Exceptions in JAX-RS applications – Mastertheboss
• Validering av inkommande data med hjälp av
Jakarta Bean Validation - Home
• Tester inkluderande tester för endpoints.
• Dockerfile för att enkelt starta applikationen som docker container.
• Docker-compose fil för att kunna starta databas + wildfly med vår applikation.
