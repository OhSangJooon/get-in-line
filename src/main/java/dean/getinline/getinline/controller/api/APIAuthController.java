package dean.getinline.getinline.controller.api;

import dean.getinline.getinline.dto.APIDataResponse;
import dean.getinline.getinline.dto.AdminRequest;
import dean.getinline.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class APIAuthController {

    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}
