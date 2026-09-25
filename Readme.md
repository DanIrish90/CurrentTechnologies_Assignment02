# Small Timer App
MCI Daniel Riml

Eine kleine mithilfe von ChatGPT erstellte Webapp mit persistenter Datenspeicherung.

## Tech Stack
### Backend
- Java
- Spring Boot
- PostgreSQL
### Frontend
- Vite
- ESLint

# STARTUP
.env.example kopieren und als .env abspeichern.

mithilfe von
```bash
docker compose up --build
``` 
die Dockercontainer bauen und starten.

Die Webapp ist dann unter
```bash
http://localhost:8081/
``` 
erreichbar.

Daten persistieren über Sitzungen hinweg.
