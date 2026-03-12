# Device Management System

## Popis projektu
**Device Management System** je backendová aplikácia postavená na **Spring Boot**, ktorá umožňuje správu zariadení, konfigurácií a compliance reportov. Projekt využíva **REST API** na komunikáciu a **WebSockety** pre notifikácie zmien v konfiguráciách a compliance reportoch.  

## Funkcionality
### Správa zariadení (`Device`)
- CRUD operácie (Create, Read, Update, Delete)

### Správa konfigurácií (`Configuration`)
- CRUD operácie cez REST API
- WebSocket notifikácie pre zmenu konfigurácie

### Správa compliance reportov (`ComplianceReport`)
- Vytváranie a získavanie reportov
- WebSocket notifikácie

### Autentifikácia a autorizácia
- Použitie **Spring Security** s **JWT tokenom**
- Role-based autorizácia pre prístup k určitým endpointom

## Technológie
- Java 17
- Spring Boot
- Spring Data JPA + Hibernate
- PostgreSQL
- Spring Security + JWT
- WebSocket (Spring)
- JUnit 5 + Mockito pre testovanie
- Maven

## Autentifikácia a autorizácia
- **Autentifikácia (Authentication):** overuje identitu používateľa pomocou **JWT tokenu**.  
- **Autorizácia (Authorization):** riadi prístup podľa **rolí** používateľa (napr. `ROLE_USER`, `ROLE_ADMIN`).  
