# Auth Service – RideLog

## 1. Purpose

The Auth Service handles authentication and authorization for the RideLog platform.  
The system is designed **Android-first** and uses **JWT-based authentication** (stateless).

This document defines:

- Authentication approach
- Current (MVP) APIs
- Future planned APIs
- Token lifecycle and security decisions

---

## 2. Authentication Strategy

### 2.1 Token Types

#### Access Token

- JWT
- Short-lived (e.g. 15 minutes)
- Sent in request header:
  Authorization: Bearer <access_token>
- Used to access protected APIs

#### Refresh Token

- Long-lived (e.g. 30–90 days)
- Stored securely on the client
- Persisted in database
- Used to generate new access tokens

---

## 3. Current APIs (MVP)

### 3.1 Register

POST /auth/register

Registers a new user using email and password.

### 3.2 Login

POST /auth/login

Authenticates user credentials and returns tokens.

### 3.3 Refresh Token

POST /auth/refresh

Issues a new access token using a valid refresh token.

### 3.4 Logout

POST /auth/logout

Invalidates the refresh token.

### 3.5 Get Current User

GET /auth/me

Returns logged-in user details.

---

## 4. Planned APIs (Post-MVP)

- Change password
- Forgot / reset password
- Logout from all devices
- Social login (Google, Apple)

---

## 5. Refresh Token Storage

Table: refresh_tokens

- id
- user_id
- token
- expires_at
- revoked
- created_at

---

## 6. Security Considerations

- BCrypt password hashing
- HTTPS only
- Stateless access tokens
- Revocable refresh tokens

---

## 7. Why JWT (Android-first)

- No session expiry issues
- Scales easily
- Industry standard for mobile apps

---

## 8. Status

- Platform: Android
- Auth Type: JWT
- Phase: MVP
