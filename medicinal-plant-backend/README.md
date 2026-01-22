# Medicinal Plant Identifier Backend

## 🌿 Overview
RESTful API backend for the Medicinal Plant Identifier application built with Spring Boot.

## 🚀 Features
- **Plant Management**: CRUD operations for medicinal plants
- **Advanced Search**: Search by name, symptoms, visual features, and image recognition
- **User Management**: Registration, authentication with JWT
- **Image Processing**: Upload and recognize plant images
- **Admin Dashboard**: Management interface for plants and users
- **API Documentation**: Interactive Swagger UI

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data JPA
- MySQL/H2 Database
- Lombok
- ModelMapper
- SpringDoc OpenAPI (Swagger)
- Maven

## 📋 Prerequisites
- JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+ (for production) or use H2 (in-memory for dev)

## 🏃 Running the Application

### Using Maven
```bash
cd medicinal-plant-backend
mvn spring-boot:run
```

### Using JAR
```bash
mvn clean package
java -jar target/medicinal-plant-backend-1.0.0.jar
```

### With Docker
```bash
docker-compose up --build
```

## 🔗 API Endpoints

### Base URL
```
http://localhost:8080
```

### Main Endpoints
- **GET** `/api/plants/all` - Get all plants
- **GET** `/api/plants/{id}` - Get plant by ID
- **GET** `/api/plants/search?query=aloe` - Search plants
- **POST** `/api/search/by-symptoms` - Search by symptoms
- **POST** `/api/images/recognize` - Image recognition
- **POST** `/api/users/register` - User registration
- **POST** `/api/users/login` - User login

### API Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

### H2 Console (Dev Mode)
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:medicinalplantdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 📂 Project Structure
```
src/main/java/com/medicinal/plant/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── service/         # Business logic
├── repository/      # Data access layer
├── model/
│   ├── entity/      # JPA entities
│   ├── dto/         # Data transfer objects
│   └── enums/       # Enumerations
├── exception/       # Custom exceptions
├── util/            # Utility classes
└── component/       # Spring components
```

## 🔧 Configuration

### Database Configuration
Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/medicinal_plant_db
    username: your_username
    password: your_password
```

### JWT Configuration
```yaml
jwt:
  secret: your_secret_key
  expiration: 86400000
```

## 🧪 Testing
```bash
mvn test
```

## 📦 Building for Production
```bash
mvn clean package -DskipTests
```

## 🐳 Docker Deployment
```bash
docker build -t medicinal-plant-backend .
docker run -p 8080:8080 medicinal-plant-backend
```

## 📝 Sample Request
```bash
curl -X GET http://localhost:8080/api/plants/all
```

## 🤝 Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License
MIT License

## 👥 Authors
Medicinal Plant Team

## 📧 Support
For support, email support@medicinalplant.com
