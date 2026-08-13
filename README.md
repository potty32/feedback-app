# Feedback-App

Kundenfeedback-Anwendung mit Angular-Frontend, Spring Boot Backend und PostgreSQL-Datenbank.

## Funktionen

- Feedback abgeben (Kundennummer, Sternebewertung 1–5, Kommentar)
- Alle Feedbacks anzeigen
- Bewertungsübersicht mit Filterung nach Sternanzahl
- Feedback löschen
- Validierung im Frontend und Backend
- Jede Kundennummer darf nur ein Feedback abgeben

## Technologie-Stack

| Schicht | Technologie |
|---|---|
| Frontend | Angular 17 |
| Backend | Spring Boot 3.2, Java 21 |
| Datenbank | PostgreSQL |
| Deployment | Railway |
| Tests | JUnit 5, Mockito, MockMvc, Karma/Jasmine |
| CI | GitHub Actions |

## Lokal starten

**PostgreSQL:**
```bash
sudo pg_ctlcluster 16 main start
```

**Backend:**
```bash
cd feedback-backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd feedback-frontend
npm start
```

Frontend läuft auf http://localhost:4200, Backend auf http://localhost:8080.

## Tests ausführen

```bash
# Backend
cd feedback-backend && mvn test

# Frontend
cd feedback-frontend && npm test -- --watch=false --browsers=ChromeHeadless
```

## Deployment

Die App läuft auf Railway:
- Frontend: https://confident-stillness-production-0ade.up.railway.app
- Backend: https://feedback-app-production-bd89.up.railway.app

Jeder Push auf `master` löst automatisch die CI-Pipeline aus (GitHub Actions).
