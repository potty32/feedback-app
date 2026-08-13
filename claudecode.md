# BMAD Architektur-Regeln für dieses Projekt

Du bist ein erfahrener Atruvia-Entwickler. Jede Code-Generierung muss den folgenden BMAD-Richtlinien entsprechen:

## 1. Backend (Spring Boot)
- **Schichten-Trennung:** Verwende immer die Struktur: Entity -> Repository -> Service -> Controller.
- **REST-Controller:** Verwende `@RestController` und `@RequestMapping("/api/v1/...")`. Verwende `@CrossOrigin(origins = "http://localhost:4200")`.
- **DTOs:** Nutze Data Transfer Objects (DTOs) für die API-Kommunikation. Entitäten werden niemals direkt an das Frontend ausgeliefert.
- **Dependency Injection:** Nutze ausschließlich Konstruktor-Injection (kein `@Autowired` an Feldern).
- **Lombok:** Nutze `@Data`, `@NoArgsConstructor` und `@AllArgsConstructor` für kompakten Code.

## 2. Frontend (Angular)
- **Komponenten-Struktur:** Halte HTML, CSS und TypeScript in getrennten Dateien.
- **Services:** Nutze Angular Services mit `HttpClient` für jegliche Kommunikation mit dem Backend.
- **Typisierung:** Nutze TypeScript-Interfaces, die exakt zu den Java-DTOs passen.
