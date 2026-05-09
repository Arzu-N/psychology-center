# Psychology Center API

This project is a backend API system built with Spring Boot.

---

## 🚀 Technologies
- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL / MySQL
- Redis
- Lombok
- Maven

---

## 📌 Features
- JWT authentication
- Refresh token system
- OTP sending (email)
- File upload system (MultipartFile)
- Redis-based rate limiting and caching
- Environment variables support

---

## ⚙️ Environment Variables

```bash
DB_PASSWORD=your_db_password
MAIL_USERNAME=your_email@gmail.com
APP_PASSWORD=your_email_app_password
JWT_SECRET=your_secret_key

```

🔐 Security
Spring Security is used
Requests are validated using JWT tokens


🧠 Redis
OTP rate limiting
Caching mechanism
Rate limiting


📦 Run
```bash
mvn clean install
mvn spring-boot:run
```
