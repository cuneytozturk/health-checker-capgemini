# Health Checker

# Known issues
- De MySQL container is niet initialized wanneer JDBC probeert te verbinden. Daardoor crasht de backend container. Herlaad deze wanneer de MySQL container volledig opgestart is.

# Prerequisites
- Docker of Podman
- Bot Framework Emulator v4 https://learn.microsoft.com/en-us/azure/bot-service/bot-service-debug-emulator?view=azure-bot-service-4.0&tabs=csharp

# Using Health Checker
1. ga naar de podman-stack folder en voer "docker compose up" uit in de command line
2. Herstart de backend container binnen Docker
3. Voeg een test oefening toe met een post request naar de backend
- POST localhost:8080/api/exercises/add
- {
    "name" : "Cobra Stretch",
    "description" : "cobra stretch",
    "imageUrl" : "image",
    "videoUrl" : "videoUrl"
}
4. Navigeer naar http://localhost:4200/exerciseschedule
5. Voeg een oefening toe aan je schema met ID 1 en voer een tijd in en klik add.
6. herlaad de pagina en je oefening zou nu tussen de geplande tijden moeten staan.

7. Open de bot-framework folder en voer "npm install" uit gevolgd door "npm start"
8. Open de Bot Emulator Framework V4 en connect met localhost:3978/api/messages
9. Je zou nu je oefening moeten ontvangen als bericht via de Bot Emulator Framework op de geplande tijd.




