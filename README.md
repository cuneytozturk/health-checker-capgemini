
# Health Checker

## 1. Description
This application uses:
- Java Spring Boot for the back-end and Scheduler service. 
- Angular for the front-end.
- MySQL as the database.
- SonarQube for code quality analysis.
- Docker for containerization.

---

## 2. Prerequisites
To run the application:
- **Docker** or **Podman** (Podman is recommended for developing within Capgemini)

For local development:
- **Java 17**
- **Maven 3.8+** 
- **Docker** or **Podman**
- **Node.js** & **npm** (for Angular development)

---

## 3. Running the Application

### 1. Clone the Repository
Clone the repository and navigate to the root folder:
```bash
git clone https://github.com/cuneytozturk/health-checker-capgemini.git
cd health-checker-capgemini
```

### 2. Use Docker Compose
1. Run the following command in a terminal to start the application with Docker Compose:
```bash
Docker compose up
```

2. Depending on your hardware the backend/scheduler containers can start earlier than the database, causing them to crash. Restart them manually in your Docker/Podman UI or run:

to check container ID:
```bash
Docker ps -a
```

and start the containers with their IDs:
```bash
Docker start "ID"
```



### 3. Confirm Application is Running
Open your web browser and navigate to **localhost:4200**
You should see the Health Checker application front-end and a list of available exercises

### 4. Receiving notifications
*To simulate receiving notifications through Teams, use the Bot Emulator Framework v4.*

1. Start the bot service by running "npm install" in a terminal in the bot-framework folder. Afterwards run "npm start" in the same folder.

2. Download the latest [Bot Framework Emulator](https://github.com/Microsoft/BotFramework-Emulator/releases/tag/v4.15.1) and start it.

3. Connect the bot service to the BFE by clicking "open bot" in the BFE UI and then connecting to the bot service port. The default URL is http://localhost:3978/api/messages

4. It should open a chat emulating a Teams chat environment.

### 5. Creating an exercise schedule

1. Navigate to **http://localhost:4200/userpreferences**

2. Configure preferences for notifications.

3. After clicking save, an exercise schedule will be generated and the application should redirect to http://localhost:4200/exerciseschedule

4. For testing notifications you can manually add an exercise schedule entry at the top of the schedule page.




---

## Local Development Setup
*Make sure the local development prerequisites are installed.*

## 1. Running integration tests
### 1.1 Integration tests

1. Start the application using chapter 3. "Running the application"
2. Stop the Scheduler/Back-end container (Depending on which you want to run integration tests for).
3. Open the Scheduler/Back-end project in your IDE.
4. Run the tests in the tests/integration folder.
5. All tests should run and be succesful.
6. Ensure the bot-service project is up & running according to chapter 3. "Running the application" in case sendNotificationReturnsSuccessMessage fails.



## 2. Sonarqube
### 2.1 Generate Sonarqube Token
1. Run the application once using docker compose.
2. Open your web browser and navigate to **localhost:9000** to access the SonarQube dashboard.
3. Log in with the default credentials:
   - **Username:** admin
   - **Password:** admin
4. Sonarqube will prompt you to change the default password.
5. After changing the password, create a local project.
6. SonarQube will generate a token for the project.


### 2.2 Configure Environment Variables
1. Create a `.env` file in the /backend directory with the following content:
```env
SONAR_TOKEN=your-sonarqube-token
```
2. TODO ANGULAR TESTS

### 2.3 Generate a SonarQube Report
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



