# Dimitri Valckenier
## Mobile App Development: Android (TIAO)

Deze repository bevat de code voor de Android app die ik ontwikkelde voor het vak "Mobile App Development: Android" aan HoGent.
De app toont enerzijds een biljart scorebord, en laat de gebruiker toe om resultaten van wedstrijden te registreren. 
De app is ontwikkeld in Android Studio.

De app is opgebouwd uit verschillende schermen:
- Een startscherm met loading spinner 
- Een scherm met het scorebord
- Een scherm met een lijst van de speeldagen in de competitie
- Een scherm met de lijst van de wedstrijden in een gekozen speeldag
- Een scherm om de resultaten van een wedstrijd te registreren

## endpoints
GET players with ranking of android season: http://localhost:9000/api/season/android
GET playingdays of android season: http://localhost:9000/api/playingday/android
GET matches of a playingday: http://localhost:9000/api/match/playingday?playingday_id=31
PUT update playingday: http://localhost:9000/api/playingday/updateandroid?playingday_id=31&is_finished=true
PUT update match: http://localhost:9000/api/match/updateandroid?match_id=131&player1FramesWon=6&player2FramesWon=5