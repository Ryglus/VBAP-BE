package ryglus.VBAP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ryglus.VBAP.DTO.user.CustomerLoginRequestDto;
import ryglus.VBAP.DTO.user.CustomerLoginResponseDto;
import ryglus.VBAP.DTO.user.CustomerRegisterRequestDto;
import ryglus.VBAP.DTO.user.CustomerRegisterResponseDto;
import ryglus.VBAP.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class CustomerController {
    private final CustomerService appUserService;

    @Autowired
    public CustomerController(CustomerService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerRegisterResponseDto> register(@RequestBody CustomerRegisterRequestDto appUserRegisterRequestDto) {
        return ResponseEntity.ok(appUserService.register(appUserRegisterRequestDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<CustomerLoginResponseDto> login(@RequestBody CustomerLoginRequestDto appUserLoginRequestDto) {
        return ResponseEntity.ok(appUserService.login(appUserLoginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody CustomerLoginRequestDto appUserLoginRequestDto) {
        return ResponseEntity.ok(appUserService.logout(appUserLoginRequestDto));
    }
}
