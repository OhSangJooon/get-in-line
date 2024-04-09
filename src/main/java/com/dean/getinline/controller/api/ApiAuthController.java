package com.dean.getinline.controller.api;

import com.dean.getinline.dto.APIDataResponse;
import com.dean.getinline.dto.AdminRequest;
import com.dean.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@RequestMapping("/api")
//@RestController
public class ApiAuthController {

    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}
