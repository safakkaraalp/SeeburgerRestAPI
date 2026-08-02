# SeeburgerRestAPI
PraxisPhase und Abschlussarbeit 

Das Projekt wurde zunächst lokal entwickelt. Das GitHub-Repository wurde erst im späteren Projektverlauf eingerichtet. Deshalb beginnt die Git-Historie mit einem konsolidierten Projektstand.

# Clearing-Center Monitoring

Diese Anwendung stellt Prozessinstanzen aus einem Clearing-Center in einer mobilen Android-App dar.

## Aktueller Funktionsumfang

- Anmeldung über das Spring-Boot-Backend
- Authentifizierung mit JWT
- Laden der Prozessdaten über eine REST-Schnittstelle
- Kundenfilter
- Systemfilter für DEV, PROD und TEST
- Zeitraumsuche
- Schnellauswahl für 1, 7 und 15 Tage
- Maximaler Suchzeitraum von 40 Tagen
- Darstellung der Prozesse in übersichtlichen Karten
- Ein- und ausblendbare technische Details

## Technischer Aufbau

- Android
- Java
- Retrofit
- Spring Boot
- Spring Security
- JWT
- JPA
- H2-Datenbank

## Nächste Erweiterung

Als nächste Erweiterung wird ein Suchfeld umgesetzt. Damit soll nach Prozessnamen, Instanz-IDs und technischen Informationen gesucht werden können.
