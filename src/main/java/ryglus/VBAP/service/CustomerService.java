package ryglus.VBAP.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ryglus.VBAP.DTO.user.CustomerLoginRequestDto;
import ryglus.VBAP.DTO.user.CustomerLoginResponseDto;
import ryglus.VBAP.DTO.user.CustomerRegisterRequestDto;
import ryglus.VBAP.DTO.user.CustomerRegisterResponseDto;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.repository.CustomerRepository;
import ryglus.VBAP.utils.JwtTokenUtil;

import java.util.Optional;

@Service
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
        if (customerRegisterRequestDto.getUsername() == null) {
            throw new RuntimeException("Username cannot be null");
        }

        Customer customer = Customer.builder()
                .name(customerRegisterRequestDto.getName())
                .address(customerRegisterRequestDto.getAddress())
                .username(customerRegisterRequestDto.getUsername()) // Set username
                .passwordHash(passwordEncoder.encode(customerRegisterRequestDto.getPassword())) // Assuming password is already hashed
                .build();

        Customer createdCustomer = customerRepository.save(customer);

        return new CustomerRegisterResponseDto(
                createdCustomer.getId(),
                createdCustomer.getName(),
                createdCustomer.getAddress(),
                createdCustomer.getUsername()
        );
    }
    public Optional<Customer> getCustomerFromJwtToken(String jwtToken) {
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return customerRepository.findByUsername(username);
    }
    public CustomerLoginResponseDto login(CustomerLoginRequestDto customerLoginRequestDto) {
        Customer foundCustomer = jwtUserDetailsService.getUserByUsername(customerLoginRequestDto.getUsername());

        if (passwordEncoder.matches(customerLoginRequestDto.getPassword(), foundCustomer.getPasswordHash())) {
            String token = jwtTokenUtil.generateToken(foundCustomer);
            return new CustomerLoginResponseDto(token, foundCustomer.getId(), foundCustomer.getUsername());
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