# Clearing Center Mobile Client

Der Clearing Center Mobile Client ist eine Android-Anwendung zur mobilen Anzeige und Filterung von Prozessinstanzen.

Die Anwendung kommuniziert über eine REST-Schnittstelle mit dem Spring-Boot-Backend und stellt die geladenen Prozessdaten in einer übersichtlichen Benutzeroberfläche dar.

## Voraussetzungen

Für die Ausführung der Anwendung werden folgende Komponenten benötigt:

- Android Studio
- Android SDK
- Java Development Kit
- Android-Emulator oder Android-Gerät
- laufendes Spring-Boot-Backend
- Internetverbindung für das erstmalige Laden der Gradle-Abhängigkeiten

## Backend-Verbindung

Die Android-Anwendung kommuniziert über eine REST-Schnittstelle mit dem Spring-Boot-Backend.

Für den Android-Emulator wird standardmäßig folgende Basis-URL verwendet:

http://10.0.2.2:8080/

## Funktionen

- Anmeldung über das Backend
- Authentifizierung mit JWT
- Speicherung des JWT-Tokens in der Android-App
- Automatische Übertragung des Tokens bei REST-Anfragen
- Laden von Prozessinstanzen über Retrofit
- Dynamischer Kundenfilter
- Systemfilter für DEV, PROD und TEST
- Filterung nach Start- und Enddatum
- Schnellauswahl für 1 Tag, 7 Tage und 15 Tage
- Maximaler Suchzeitraum von 40 Tagen
- Verhinderung zukünftiger Datumsangaben
- Darstellung der Ergebnisse in Prozesskarten
- Anzeige von Kunde, System, Startzeit und letzter Aktion
- Anzeige des Erstellers und des verantwortlichen Benutzers
- Ein- und ausblendbare technische Details
- Manuelle Aktualisierung der Prozessdaten


