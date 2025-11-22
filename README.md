# Redis Cache Demo Project

This is a demo project built with Spring Boot, demonstrating how to use Redis caching with a PostgreSQL database. The project includes basic user management functionality like creating, updating, retrieving, and deleting users. Redis is used as a cache to improve performance.

## Features

- Create a new user.
- Retrieve a list of all users.
- Retrieve a specific user by ID.
- Update user information.
- Delete a user by ID.

## Technologies Used

- **Spring Boot** (for building the application)
- **Spring Data JPA** (for database interactions)
- **PostgreSQL** (as the relational database)
- **Redis** (for caching)
- **Jedis** (Redis client for Java)
- **Lombok** (for reducing boilerplate code)

## Setup & Installation

### Prerequisites

- **Java 17** or higher
- **Maven** for dependency management
- **Redis** server installed and running locally or on a remote server
- **PostgreSQL** database setup with a configured schema

## application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/redis_cache_demo
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.redis.host=localhost
spring.redis.port=6379

 
 ### API Endpoints
Method	Endpoint	
POST	/api/v1/users	
GET	/api/v1/users	
GET	/api/v1/users/{id}	
PUT	/api/v1/users	
DELETE	/api/v1/users/{id}	




### Clone the Repository

```bash
git clone https://github.com/yourusername/redis-cache.git
cd redis-cache




