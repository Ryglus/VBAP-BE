package ryglus.VBAP.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ryglus.VBAP.DTO.user.CustomerLoginRequestDto;
import ryglus.VBAP.DTO.user.CustomerLoginResponseDto;
import ryglus.VBAP.DTO.user.CustomerRegisterRequestDto;
import ryglus.VBAP.DTO.user.CustomerRegisterResponseDto;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.repository.CustomerRepository;
import ryglus.VBAP.utils.JwtTokenUtil;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(
            CustomerRepository appUserRepository,
            JwtUserDetailsService jwtUserDetailsService,
            JwtTokenUtil jwtTokenUtil,
            BCryptPasswordEncoder passwordEncoder

    ) {
        this.customerRepository = appUserRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;

    }

    public CustomerRegisterResponseDto register(CustomerRegisterRequestDto customerRegisterRequestDto) {
        Customer customer = Customer.builder()
                .name(customerRegisterRequestDto.getName())
                .address(customerRegisterRequestDto.getAddress())
                .email(customerRegisterRequestDto.getEmail())
                .passwordHash(passwordEncoder.encode(customerRegisterRequestDto.getPassword())) // Assuming password is already hashed
                .build();

        Customer createdCustomer = customerRepository.save(customer);

        return new CustomerRegisterResponseDto(
                createdCustomer.getId(),
                createdCustomer.getName(),
                createdCustomer.getAddress(),
                createdCustomer.getEmail()
        );
    }

    public CustomerLoginResponseDto login(CustomerLoginRequestDto customerLoginRequestDto) {
        Customer foundCustomer = jwtUserDetailsService.getUserByUsername(customerLoginRequestDto.getUsername());

        if (foundCustomer != null && foundCustomer.getPasswordHash().equals(customerLoginRequestDto.getPassword())) {
            String token = jwtTokenUtil.generateToken(foundCustomer);
            return new CustomerLoginResponseDto(token, foundCustomer.getId(), foundCustomer.getEmail());
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public boolean logout(@NotNull CustomerLoginRequestDto appUserLoginRequestDto) {
        Customer foundUser = jwtUserDetailsService.getUserByUsername(appUserLoginRequestDto.getUsername());

        return true;
    }

    public void invalidateJwtTokens(@NotNull Customer appUser) {
        appUser.setJwtVersion(appUser.getJwtVersion() + 1);
        customerRepository.save(appUser);
    }
}