package com.restfulbooker.service;

import com.restfulbooker.model.TestResult;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

@Service
public class PetstoreTestService {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private Long petId;
    private Long orderId;
    private String username = "testuser" + System.currentTimeMillis();

    // ==================== PET TESTS ====================

    public TestResult testAddPet() {
        TestResult result = new TestResult();
        result.setTestName("Add New Pet");
        long startTime = System.currentTimeMillis();

        try {
            petId = System.currentTimeMillis();

            String requestBody = "{\n" +
                    "  \"id\": " + petId + ",\n" +
                    "  \"name\": \"Fluffy\",\n" +
                    "  \"status\": \"available\",\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"Dogs\"\n" +
                    "  },\n" +
                    "  \"photoUrls\": [\"https://example.com/photo.jpg\"],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"name\": \"friendly\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post(BASE_URL + "/pet");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Pet 'Fluffy' created successfully with ID: " + petId);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to create pet. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testGetPet() {
        TestResult result = new TestResult();
        result.setTestName("Get Pet by ID");
        long startTime = System.currentTimeMillis();

        try {
            if (petId == null) {
                TestResult createResult = testAddPet();
                if (!createResult.getStatus().equals("PASSED")) {
                    result.setStatus("ERROR");
                    result.setMessage("Failed to create pet for get test");
                    return result;
                }
            }

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get(BASE_URL + "/pet/" + petId);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                String petName = response.path("name");
                result.setStatus("PASSED");
                result.setMessage("Successfully retrieved pet: " + petName);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to get pet");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testUpdatePet() {
        TestResult result = new TestResult();
        result.setTestName("Update Pet");
        long startTime = System.currentTimeMillis();

        try {
            TestResult addResult = testAddPet();
            if (!addResult.getStatus().equals("PASSED")) {
                result.setStatus("ERROR");
                result.setMessage("Failed to create pet for update test");
                return result;
            }

            String requestBody = "{\n" +
                    "  \"id\": " + petId + ",\n" +
                    "  \"name\": \"Fluffy Updated\",\n" +
                    "  \"status\": \"sold\",\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"Dogs\"\n" +
                    "  },\n" +
                    "  \"photoUrls\": [\"https://example.com/photo.jpg\"]\n" +
                    "}";

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .put(BASE_URL + "/pet");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Pet updated successfully to 'Fluffy Updated' with status 'sold'");
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to update pet");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testFindPetsByStatus() {
        TestResult result = new TestResult();
        result.setTestName("Find Pets by Status");
        long startTime = System.currentTimeMillis();

        try {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .queryParam("status", "available")
                    .when()
                    .get(BASE_URL + "/pet/findByStatus");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());

            if (response.getStatusCode() == 200) {
                int count = response.jsonPath().getList("$").size();
                result.setStatus("PASSED");
                result.setMessage("Found " + count + " pets with status 'available'");
                result.setResponseBody("Available pets count: " + count);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to find pets by status");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testDeletePet() {
        TestResult result = new TestResult();
        result.setTestName("Delete Pet");
        long startTime = System.currentTimeMillis();

        try {
            TestResult addResult = testAddPet();
            if (!addResult.getStatus().equals("PASSED")) {
                result.setStatus("ERROR");
                result.setMessage("Failed to create pet for delete test");
                return result;
            }

            Long idToDelete = petId;

            Response response = given()
                    .contentType(ContentType.JSON)
                    .header("api_key", "special-key")
                    .when()
                    .delete(BASE_URL + "/pet/" + idToDelete);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Pet " + idToDelete + " deleted successfully");
                petId = null;
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to delete pet");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testPetNotFound() {
        TestResult result = new TestResult();
        result.setTestName("Pet Not Found (404)");
        long startTime = System.currentTimeMillis();

        try {
            // Use a very unique ID that definitely won't exist
            long nonExistentId = Long.MAX_VALUE - System.currentTimeMillis();

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get(BASE_URL + "/pet/" + nonExistentId);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 404) {
                result.setStatus("PASSED");
                result.setMessage("API correctly returned 404 for non-existent pet ID: " + nonExistentId);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Expected 404 for non-existent pet, got " + response.getStatusCode() + " for ID: " + nonExistentId);
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    // ==================== STORE TESTS ====================

    public TestResult testPlaceOrder() {
        TestResult result = new TestResult();
        result.setTestName("Place Order");
        long startTime = System.currentTimeMillis();

        try {
            // Generate a new unique order ID each time (1-10 range)
            orderId = (System.currentTimeMillis() % 10) + 1;

            String requestBody = "{\n" +
                    "  \"id\": " + orderId + ",\n" +
                    "  \"petId\": 123,\n" +
                    "  \"quantity\": 1,\n" +
                    "  \"shipDate\": \"2024-12-01T00:00:00.000Z\",\n" +
                    "  \"status\": \"placed\",\n" +
                    "  \"complete\": true\n" +
                    "}";

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post(BASE_URL + "/store/order");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Order placed successfully with ID: " + orderId);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to place order");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testGetOrder() {
        TestResult result = new TestResult();
        result.setTestName("Get Order by ID");
        long startTime = System.currentTimeMillis();

        try {
            if (orderId == null) {
                TestResult placeResult = testPlaceOrder();
                if (!placeResult.getStatus().equals("PASSED")) {
                    result.setStatus("ERROR");
                    result.setMessage("Failed to place order for get test");
                    return result;
                }
            }

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get(BASE_URL + "/store/order/" + orderId);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Successfully retrieved order ID: " + orderId);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to get order");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testGetInventory() {
        TestResult result = new TestResult();
        result.setTestName("Get Store Inventory");
        long startTime = System.currentTimeMillis();

        try {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get(BASE_URL + "/store/inventory");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Successfully retrieved store inventory");
                result.setResponseBody("Inventory data retrieved");
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to get inventory");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testDeleteOrder() {
        TestResult result = new TestResult();
        result.setTestName("Delete Order");
        long startTime = System.currentTimeMillis();

        try {
            // Always create a fresh order first
            orderId = null; // Reset to ensure fresh creation
            TestResult placeResult = testPlaceOrder();
            if (!placeResult.getStatus().equals("PASSED")) {
                result.setStatus("ERROR");
                result.setMessage("Failed to create order for delete test");
                result.setDuration(System.currentTimeMillis() - startTime);
                return result;
            }

            Long idToDelete = orderId;

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(BASE_URL + "/store/order/" + idToDelete);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("Order " + idToDelete + " deleted successfully");
                orderId = null;
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to delete order ID: " + idToDelete);
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    // ==================== USER TESTS ====================

    public TestResult testCreateUser() {
        TestResult result = new TestResult();
        result.setTestName("Create User");
        long startTime = System.currentTimeMillis();

        try {
            username = "testuser" + System.currentTimeMillis();

            String requestBody = "{\n" +
                    "  \"id\": 12345,\n" +
                    "  \"username\": \"" + username + "\",\n" +
                    "  \"firstName\": \"John\",\n" +
                    "  \"lastName\": \"Doe\",\n" +
                    "  \"email\": \"john@example.com\",\n" +
                    "  \"password\": \"password123\",\n" +
                    "  \"phone\": \"1234567890\",\n" +
                    "  \"userStatus\": 1\n" +
                    "}";

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post(BASE_URL + "/user");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("User '" + username + "' created successfully");
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to create user");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testGetUser() {
        TestResult result = new TestResult();
        result.setTestName("Get User by Name");
        long startTime = System.currentTimeMillis();

        try {
            TestResult createResult = testCreateUser();
            if (!createResult.getStatus().equals("PASSED")) {
                result.setStatus("ERROR");
                result.setMessage("Failed to create user for get test");
                return result;
            }

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get(BASE_URL + "/user/" + username);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                String firstName = response.path("firstName");
                result.setStatus("PASSED");
                result.setMessage("Successfully retrieved user: " + firstName);
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to get user");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testUpdateUser() {
        TestResult result = new TestResult();
        result.setTestName("Update User");
        long startTime = System.currentTimeMillis();

        try {
            TestResult createResult = testCreateUser();
            if (!createResult.getStatus().equals("PASSED")) {
                result.setStatus("ERROR");
                result.setMessage("Failed to create user for update test");
                return result;
            }

            String requestBody = "{\n" +
                    "  \"id\": 12345,\n" +
                    "  \"username\": \"" + username + "\",\n" +
                    "  \"firstName\": \"Jane\",\n" +
                    "  \"lastName\": \"Smith\",\n" +
                    "  \"email\": \"jane@example.com\",\n" +
                    "  \"password\": \"newpassword\",\n" +
                    "  \"phone\": \"9876543210\",\n" +
                    "  \"userStatus\": 1\n" +
                    "}";

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .put(BASE_URL + "/user/" + username);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("User updated successfully to Jane Smith");
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to update user");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testDeleteUser() {
        TestResult result = new TestResult();
        result.setTestName("Delete User");
        long startTime = System.currentTimeMillis();

        try {
            TestResult createResult = testCreateUser();
            if (!createResult.getStatus().equals("PASSED")) {
                result.setStatus("ERROR");
                result.setMessage("Failed to create user for delete test");
                return result;
            }

            Response response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(BASE_URL + "/user/" + username);

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("User '" + username + "' deleted successfully");
                username = "testuser" + System.currentTimeMillis();
            } else {
                result.setStatus("FAILED");
                result.setMessage("Failed to delete user");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    public TestResult testUserLogin() {
        TestResult result = new TestResult();
        result.setTestName("User Login");
        long startTime = System.currentTimeMillis();

        try {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .queryParam("username", "testuser")
                    .queryParam("password", "password123")
                    .when()
                    .get(BASE_URL + "/user/login");

            long duration = System.currentTimeMillis() - startTime;
            result.setDuration(duration);
            result.setStatusCode(response.getStatusCode());
            result.setResponseBody(response.getBody().asString());

            if (response.getStatusCode() == 200) {
                result.setStatus("PASSED");
                result.setMessage("User logged in successfully");
            } else {
                result.setStatus("FAILED");
                result.setMessage("Login failed");
            }
        } catch (Exception e) {
            result.setStatus("ERROR");
            result.setMessage("Exception: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - startTime);
        }

        return result;
    }
}