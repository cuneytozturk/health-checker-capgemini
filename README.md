
# Health Checker

## Description
This application uses:
- Java Spring Boot for the back-end and Scheduler service. 
- Angular for the front-end.
- MySQL as the database.
- SonarQube for code quality analysis.
- Docker for containerization.

---

## Prerequisites
To run the application:
- **Docker** or **Podman**

For local development:
- **Java 17**
- **Maven 3.8+** 
- **Docker** or **Podman**
- **Node.js** & **npm** (for Angular development)

---

## Running the Application

### 1. Clone the Repository
Clone the repository and navigate to the root folder:
```bash
git clone https://github.com/cuneytozturk/health-checker-capgemini.git
cd health-checker-capgemini
```

### 2. Use Docker Compose
Run the following command to start the application with Docker Compose:
```bash
Docker compose up
```

### 3. Confirm Application is Running
Open your web browser and navigate to **localhost:4200**
You should see the Health Checker application front-end and a list of available exercises

---

## Local Development Setup
*Make sure the local development prerequisites are installed.*
## 1. Sonarqube
### 1.1 Generate Sonarqube Token
1. Run the application once using docker compose.
2. Open your web browser and navigate to **localhost:9000** to access the SonarQube dashboard.
3. Log in with the default credentials:
   - **Username:** admin
   - **Password:** admin
4. Sonarqube will prompt you to change the default password.
5. After changing the password, create a local project.
6. SonarQube will generate a token for the project.


### 1.2 Configure Environment Variables
1. Create a `.env` file in the /backend directory with the following content:
```env
SONAR_TOKEN=your-sonarqube-token
```
2. TODO ANGULAR TESTS

### 1.3 Generate a SonarQube Report
*Ensure Docker is running so the integration tests can be reported in SonarQube*
1. navigate to the /backend directory and execute in terminal:
```bash
mvn clean verify sonar:sonar
```
Sonarqube will run the tests and generate a report based on the code analysis.

---

## Notes
- Add the `.env` file to `.gitignore` to avoid committing sensitive information.

## 2. Debug backend while running as Docker container with hot reload
*Only configured for the backend service. uses port 5005 which can be configured in the pom.xml*

1. Rename the Dockerfile.old to Dockerfile. This Dockerfile is configured to enable remote debugging and hot reloading for the backend service.
2. Rebuild the Docker image and run it.
3. Add a remote debug run configuration in your IDE (e.g., IntelliJ IDEA, Eclipse) with the following settings:
   - **Host:** localhost
   - **Port:** 5005
4. You should now be able to debug the backend/scheduler service while it is running in the Docker container.
5. Hot reloading is enabled, so you can make changes to the code and see them reflected immediately without restarting the container.

---



