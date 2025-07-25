# 🧠 Notes App – Spring Boot Backend + React UI

> ⚠️ **Frontend (React)** – _under active development_  
> ✅ **Backend (Spring Boot)** – _mostly implemented, still being improved_

---

## ✅ Project Overview

This is a secure notes management REST API built with **Spring Boot**.  
The backend handles user authentication and note operations (CRUD) using modern Spring Security practices with **JWT tokens**.  
All user data is stored in a **MySQL** database, while **H2** is used only for isolated repository testing.

---

## 🔐 Authentication & Security

- **JWT-based authentication** with custom `AuthTokenFilter` and `AuthEntryPointJwt`
- **AuthenticationManager** setup using `UsernamePasswordAuthenticationToken`
- Users are required to sign in via `/api/auth/public/signin` and receive a valid JWT token
- Roles supported: `ROLE_USER`, `ROLE_ADMIN`
- **BCrypt** password hashing
- User account state fields managed:  
  `accountNonLocked`, `accountNonExpired`, `credentialsNonExpired`, `enabled`
- All secured endpoints require a valid JWT token (tested via Postman)

---

## 📦 Backend Features

### Auth Endpoints
- `POST /api/auth/public/signup` – Register new user (with optional role: "admin" or default: "user")
- `POST /api/auth/public/signin` – Login and receive a JWT token
- `GET /api/auth/username` – Return current authenticated username
- `GET /api/auth/user` – Return full user profile with status flags and roles

### Notes Endpoints (Authenticated)
- `POST /api/notes` – Create a note
- `GET /api/notes` – List all notes for current user
- `PUT /api/notes/{noteId}` – Update a note by ID
- `DELETE /api/notes/{noteId}` – Delete a note by ID

Each endpoint uses `@AuthenticationPrincipal` to get the current `UserDetails`.

---

## 🧪 Testing

- **H2 in-memory DB** configured for unit and integration tests
- **Postman** used for testing all flows: signup, login, token verification, secured CRUD
- Error handling via `ApiException`, including meaningful status codes and messages

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (`jjwt` 0.12.6)
- MySQL (main DB)
- H2 (for testing only)
- JPA / Hibernate
- Lombok
- ModelMapper
- JUnit 5
- Postman (for API test collection)

---

## 📌 Status

✅ Core backend logic is working  
🚧 Backend still being improved (e.g. unit tests, validation, email confirmation)  
🧱 UI (React) is planned and currently in progress

---

**Made with ❤️ by raxrot**  
_This README will be updated as development continues._