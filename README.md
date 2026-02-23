# 🔗 URL Shortener Backend API

A production-ready URL Shortener backend built using Spring Boot, JWT Authentication, PostgreSQL, and Docker.

This backend provides secure URL shortening, public redirection, click tracking, and date-wise analytics.

---

## 🚀 Live Deployment

Health Check:
https://url-shortener-sb-osbl.onrender.com/health

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL (Neon DB)
- Docker
- Render (Cloud Deployment)
- Maven

---

## 🔐 Features

- User Registration
- User Login with JWT Token
- Secure API Endpoints
- Short URL Generation
- Public URL Redirection
- Click Tracking
- Date-wise Analytics
- Total Click Aggregation
- Dockerized Deployment
- Public Health Endpoint

---

## 📌 API Endpoints

### 🔓 Public Endpoints

1️⃣ Register User  
POST `/api/auth/public/register`

Request Body:
{
"username": "john_doe",
"email": "john@example.com",
"password": "password123"
}

Response:
User Registered Successfully

---

2️⃣ Login User  
POST `/api/auth/public/login`

Request Body:
{
"username": "john_doe",
"password": "password123"
}

Response:
{
"token": "JWT_TOKEN_HERE"
}

---

3️⃣ Public URL Redirect  
GET `/{shortUrl}`

Example:
https://url-shortener-sb-osbl.onrender.com/Np06EhXk

Redirects to original URL and records click event.

---

4️⃣ Health Check  
GET `/health`

Response:
{
"status": "UP",
"message": "Backend API is running successfully",
"service": "URL Shortener Backend API",
"authentication": "JWT Enabled",
"features": "URL Shortening, Redirect, Analytics",
"author": "Sarthak Sinha",
"timestamp": "2026-02-24T05:02:41"
}

---

## 🔐 Protected Endpoints (Require JWT)

All protected APIs require:

Authorization: Bearer <JWT_TOKEN>

---

1️⃣ Create Short URL  
POST `/api/urls/shorten`

Request Body:
{
"originalUrl": "https://google.com"
}

Response:
{
"id": 3,
"shortUrl": "Np06EhXk",
"originalUrl": "https://google.com",
"clickCount": 0,
"createdDate": "2026-02-24T03:18:51",
"username": "john_doe"
}

---

2️⃣ Get User URLs  
GET `/api/urls/myurls`

Returns all URLs created by logged-in user.

---

3️⃣ Get URL Analytics  
GET `/api/urls/analytics/{shortUrl}?startDate=YYYY-MM-DDTHH:MM:SS&endDate=YYYY-MM-DDTHH:MM:SS`

Example:
/api/urls/analytics/Np06EhXk?startDate=2026-02-23T00:00:00&endDate=2026-02-23T23:59:59

Response:
[
{
"clickDate": "2026-02-23",
"count": 2
}
]

---

4️⃣ Get Total Clicks By Date  
GET `/api/urls/totalClicks?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

Example:
/api/urls/totalClicks?startDate=2026-02-23&endDate=2026-02-24

Response:
{
"2026-02-23": 2,
"2026-02-24": 1
}

---

## 🗄 Database Schema

User
- id
- username
- email
- password
- role

UrlMapping
- id
- originalUrl
- shortUrl
- clickCount
- createdDate
- user_id (FK)

ClickEvent
- id
- clickDate
- url_mapping_id (FK)

---

## 🐳 Docker Commands

Build Image:
docker build -t url-shortener-sb .

Tag Image:
docker tag url-shortener-sb sarthaksinha2003/url-shortener-sb:latest

Push to Docker Hub:
docker push sarthaksinha2003/url-shortener-sb:latest

---

## 🧠 Architecture

Controller Layer → REST APIs  
Service Layer → Business Logic  
Repository Layer → Database Access  
JWT Filter → Authentication & Authorization  
ClickEvent Table → Analytics Tracking

---

## 📈 Future Improvements

- Redis Caching
- Rate Limiting
- Swagger Documentation
- Microservices Architecture
- CI/CD Pipeline

---

## 👨‍💻 Author

Sarthak Sinha  
Backend Developer | Java | Spring Boot

---

⭐ If you like this project, feel free to star the repository!