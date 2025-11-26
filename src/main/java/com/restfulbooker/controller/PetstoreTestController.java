package com.restfulbooker.controller;

import com.restfulbooker.model.TestResult;
import com.restfulbooker.service.PetstoreTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "*")
public class PetstoreTestController {

    @Autowired
    private PetstoreTestService petstoreService;

    // ==================== PET ENDPOINTS ====================

    @GetMapping("/petstore-add-pet")
    public TestResult runAddPet() {
        return petstoreService.testAddPet();
    }

    @GetMapping("/petstore-get-pet")
    public TestResult runGetPet() {
        return petstoreService.testGetPet();
    }

    @GetMapping("/petstore-update-pet")
    public TestResult runUpdatePet() {
        return petstoreService.testUpdatePet();
    }

    @GetMapping("/petstore-find-by-status")
    public TestResult runFindByStatus() {
        return petstoreService.testFindPetsByStatus();
    }

    @GetMapping("/petstore-delete-pet")
    public TestResult runDeletePet() {
        return petstoreService.testDeletePet();
    }

    @GetMapping("/petstore-pet-not-found")
    public TestResult runPetNotFound() {
        return petstoreService.testPetNotFound();
    }

    // ==================== STORE ENDPOINTS ====================

    @GetMapping("/petstore-place-order")
    public TestResult runPlaceOrder() {
        return petstoreService.testPlaceOrder();
    }

    @GetMapping("/petstore-get-order")
    public TestResult runGetOrder() {
        return petstoreService.testGetOrder();
    }

    @GetMapping("/petstore-inventory")
    public TestResult runGetInventory() {
        return petstoreService.testGetInventory();
    }

    @GetMapping("/petstore-delete-order")
    public TestResult runDeleteOrder() {
        return petstoreService.testDeleteOrder();
    }

    // ==================== USER ENDPOINTS ====================

    @GetMapping("/petstore-create-user")
    public TestResult runCreateUser() {
        return petstoreService.testCreateUser();
    }

    @GetMapping("/petstore-get-user")
    public TestResult runGetUser() {
        return petstoreService.testGetUser();
    }

    @GetMapping("/petstore-update-user")
    public TestResult runUpdateUser() {
        return petstoreService.testUpdateUser();
    }

    @GetMapping("/petstore-delete-user")
    public TestResult runDeleteUser() {
        return petstoreService.testDeleteUser();
    }

    @GetMapping("/petstore-user-login")
    public TestResult runUserLogin() {
        return petstoreService.testUserLogin();
    }
}