# Multi-Site API Testing Dashboard 🚀

A beautiful web-based API testing dashboard built with Spring Boot and REST Assured for testing multiple REST APIs. Currently supports Restful Booker API and Petstore API with easy site switching.

## Features
- 🎨 Modern, responsive UI with gradient design and smooth animations
- 🔄 Multi-site support - switch between different APIs seamlessly
- ✅ 25 comprehensive API tests across 2 different APIs
- 📊 Real-time test results with statistics tracking
- 🔴🟢 Color-coded pass/fail indicators for instant feedback
- ⚡ Individual test execution or run all tests at once
- 🔁 "Run Again" functionality for repeated testing
- 🔐 Automatic authentication handling
- 📱 Fully responsive design for all screen sizes

## Technologies
- **Backend:** Java 21, Spring Boot 3.4.0
- **Testing:** REST Assured 5.5.0, JUnit 5
- **Frontend:** HTML5, CSS3, JavaScript (ES6+)
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA

## Setup & Installation

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- IntelliJ IDEA (recommended) or any Java IDE

### Steps
1. **Clone the repository**
```bash
   git clone https://github.com/YOUR-USERNAME/API-Testing-Dashboard.git
   cd API-Testing-Dashboard
```

2. **Open in IntelliJ IDEA**
    - File → Open → Select project folder
    - Wait for Maven to download dependencies

3. **Run the application**
    - Navigate to `src/main/java/com/restfulbooker/Application.java`
    - Right-click → Run 'Application'
    - Or use Maven: `mvn spring-boot:run`

4. **Access the dashboard**
    - Open browser: http://localhost:8081
    - Select your desired API and start testing!

## API Test Suites

### 🏨 Restful Booker API (10 Tests)
Testing hotel booking management system

**Health & CRUD Operations:**
- 🏥 Health Check - API availability test
- ➕ Create Booking - POST new booking
- 🔍 Get Booking by ID - Retrieve specific booking
- 📋 Get All Bookings - List all bookings
- 🔎 Filter by Name - Query bookings by name

**Update & Delete:**
- ✏️ Update Booking (PUT) - Full update
- 📝 Partial Update (PATCH) - Partial update
- 🗑️ Delete Booking - Remove booking

**Edge Cases:**
- ❌ Invalid Data Test - Handle bad requests
- 🚫 Non-Existent Test - 404 handling

### 🐾 Petstore API (15 Tests)
Testing pet store management system (Swagger Petstore)

**Pet Management (6 tests):**
- ➕ Add New Pet - Create pet profile
- 🔍 Get Pet by ID - Retrieve pet details
- ✏️ Update Pet - Modify pet information
- 📋 Find Pets by Status - Filter by availability
- 🗑️ Delete Pet - Remove pet from store
- 🚫 Pet Not Found (404) - Error handling

**Store/Order Management (4 tests):**
- 🛒 Place Order - Create purchase order
- 📦 Get Order by ID - Retrieve order details
- 📊 Get Store Inventory - View stock levels
- 🗑️ Delete Order - Cancel order

**User Management (5 tests):**
- 👤 Create User - Register new user
- 👁️ Get User by Name - Retrieve user profile
- ✏️ Update User - Modify user information
- 🗑️ Delete User - Remove user account
- 🔐 User Login - Authentication test

## Project Structure
```
API-Testing/
├─── src/
│   ├─── main/
│   │   ├─── java/
│   │   │   ├─── com/
│   │   │   │   └─── restfulbooker/
│   │   │   │       │    Application.java # Main Spring Boot app
│   │   │   │       │
│   │   │   │       ├─── controller/
│   │   │   │       │        PetstoreTestController.java # REST endpoints for Petstore tests
│   │   │   │       │        TestController.java # REST endpoints for Restful Booker tests
│   │   │   │       │
│   │   │   │       ├─── model/
│   │   │   │       │        TestResult.java # Plain Old Java Object for test result data
│   │   │   │       │
│   │   │   │       └─── service/
│   │   │   │                ApiTestService.java # Business logic for Resful Booker API tests
│   │   │   │                PetstoreTestService.java # Business logic for Petstore API tests
│   │   │   │
│   │   │   └─── org/
│   │   │       └─── example/
│   │   │                Main.java
│   │   │
│   │   └─── resources/
│   │       │    application.properties # Spring Boot configuration (port, app name)
│   │       │
│   │       └─── static/
│   │                index.html # Front-end dashboard UI
│   │
│   └─── test/
│       └─── java/
│           └─── com/
│               └─── restfulbooker/
│                   └─── tests/
│                            RestfulBookerTests.java # JUnit tests for CLI testing
└─── pom.xml # Maven dependencies and build configuration
```

## Usage

### Running Individual Tests
1. Select your desired API (Restful Booker or Petstore)
2. Click any test card's "Run Test" button
3. View results instantly below the button

### Running All Tests
1. Select your desired API
2. Click the green "▶️ Run All Tests" button
3. Watch tests execute sequentially with progress indicator
4. Click "🔄 Run Again" to repeat the test suite

### Switching Between APIs
- Click the API selector buttons at the top
- Test cards update automatically
- Statistics reset for the new API

## Configuration

### Changing Server Port
Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Adding New APIs
1. Create new Service class (e.g., `YourApiTestService.java`)
2. Create new Controller class (e.g., `YourApiTestController.java`)
3. Update `index.html` - add to `testSuites` object:
```javascript
'your-api': {
    name: 'Your API Name',
    apiUrl: 'https://your-api.com',
    tests: [
        { name: 'test-1', display: 'Test 1', icon: '🔷', iconClass: 'icon-create' }
    ]
}
```

## API Endpoints
The dashboard exposes REST endpoints for each test:

**Restful Booker:**
- `GET /api/tests/health`
- `GET /api/tests/create-booking`
- `GET /api/tests/get-booking`
- ... (and 7 more)

**Petstore:**
- `GET /api/tests/petstore-add-pet`
- `GET /api/tests/petstore-get-pet`
- ... (and 13 more)

## Testing Philosophy
- ✅ Each test is independent and can run multiple times
- ✅ Tests create their own test data (no external dependencies)
- ✅ Proper cleanup after destructive operations
- ✅ Authentication handled automatically
- ✅ Comprehensive error handling and reporting

## Screenshots

### Main Dashboard
![Dashboard](https://github.com/user-attachments/assets/96b86bc7-81fd-423f-a4ed-c26fb0d2d0d5)
*Main Dashboard Restful Booker showing site selector and test cards* 

![Dashboard](https://github.com/user-attachments/assets/8176a3b0-1439-473a-adb6-ee98aefd062d)
*Main Dashboard Petstore API showing site selector and test cards*

### Test Result
![Test Results](https://github.com/user-attachments/assets/2a684aa4-5663-48fb-82d0-ccad38714540)
*Example test execution of Restful Booker with detailed results*

![Test Results](https://github.com/user-attachments/assets/4170e3f5-8a9d-4abb-a0cd-ebc988b5ac32)
*Example test execution of Petstore API with detailed results*


## Troubleshooting

**Issue: Tests fail with 403 Forbidden**
- Solution: Restart the Spring Boot application to refresh auth tokens

**Issue: Petstore tests fail with 404**
- Solution: Verify the Petstore API is accessible at https://petstore.swagger.io/v2

**Issue: Port 8081 already in use**
- Solution: Change port in `application.properties` or kill process using port 8081

## Future Enhancements
- [ ] Add more API integrations (ReqRes, JSONPlaceholder, etc.)
- [ ] Export test results to PDF/CSV
- [ ] Test history and analytics
- [ ] Scheduled test execution
- [ ] Custom test configuration UI
- [ ] Performance metrics (response time charts)

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is open source and available under the [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE).

## Author
**Your Name**
- GitHub: [@leonardushutabarat](https://github.com/leonardushutabarat)
- LinkedIn: [Leonardus Hutbarat](https://www.linkedin.com/in/leonardus-hutabarat/)

## Acknowledgments
- [Restful Booker API](https://restful-booker.herokuapp.com/) - Practice API for testing
- [Petstore API](https://petstore.swagger.io/) - Swagger demo API
- [REST Assured](https://rest-assured.io/) - Java DSL for API testing
- [Spring Boot](https://spring.io/projects/spring-boot) - Application framework

---

⭐ **Star this repository if you found it helpful!**

