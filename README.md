# 🔗 URL Redirection Traffic Control Service

A scalable URL shortening service built using **Java, Spring Boot, JPA, Redis, MySQL, and Docker**.  
This system provides secure URL shortening with JWT authentication and distributed rate limiting to prevent abuse.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL
- Redis
- Docker
- Maven
- Postman (API Testing)

---

## 🏗 Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database

- **Controller Layer** – Handles REST APIs
- **Service Layer** – Business logic
- **Repository Layer** – Database interaction
- **Security Layer** – JWT authentication & authorization
- **Redis Layer** – Rate limiting & caching

---

## ✨ Features

- 🔐 JWT-based Authentication (Login & Register)
- 🔗 Base62 URL Shortening
- ⚡ Sliding Window Rate Limiting (Redis-backed)
- 📊 Click Tracking Support
- 🛡 Role-based Authorization (ROLE_USER)
- 🐳 Dockerized Deployment
- 📦 Clean Layered Architecture

---

## 📌 API Endpoints

### 🔐 Authentication APIs

**Register User**
```
POST /api/auth/public/register
```

**Login User**
```
POST /api/auth/public/login
```

---

### 🔗 URL APIs

**Create Short URL**
```
POST /api/url/create
```

**Redirect to Original URL**
```
GET /{shortCode}
```

---

## ⚡ Rate Limiting Implementation

Implemented a **Sliding Window Rate Limiting algorithm** using Redis.

- Tracks requests per user/IP
- Prevents abuse attacks
- Distributed-safe (works across multiple instances)
- Optimized request validation using Redis sorted sets

This ensures high performance while protecting backend resources.

---

## 🗄 Database Design

### User Table
- id
- username
- email
- password
- role

### URL Mapping Table
- id
- originalUrl
- shortCode
- userId
- createdAt

### Click Event Table
- id
- urlId
- timestamp
- ipAddress

---

## 🔐 Security

- Password encryption using BCrypt
- JWT token-based authentication
- Stateless session management
- Protected endpoints for authenticated users

---

## 🛠 How to Run Locally

### 1️⃣ Clone the Repository
```
git clone https://github.com/your-username/url-shortener.git
cd url-shortener
```

### 2️⃣ Configure MySQL
Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3️⃣ Start Redis
Make sure Redis is running locally:
```
redis-server
```

### 4️⃣ Run the Application
```
mvn spring-boot:run
```

Application will start at:
```
http://localhost:8080
```

---

## 🐳 Run with Docker (Optional)

```
docker-compose up --build
```

---

## 📈 Future Improvements

- URL expiration feature
- Custom short codes
- Analytics dashboard
- Geo-based click tracking
- Monitoring using Prometheus & Grafana

---

## 👨‍💻 Author

Sarthak Sinha  
B.Tech | Backend Developer  
Passionate about scalable system design & distributed systems.

---

## 📜 License

This project is for educational and learning purposes.
