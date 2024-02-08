package ryglus.VBAP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.repository.CustomerRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final CustomerRepository appUserRepository;

    @Autowired
    public JwtUserDetailsService(CustomerRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Customer getUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User " + username + " not found."));
    }
}