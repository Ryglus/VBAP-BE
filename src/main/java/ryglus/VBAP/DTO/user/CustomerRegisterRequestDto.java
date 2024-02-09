package ryglus.VBAP.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterRequestDto {
    private String username; // Aligning with the email field in the Customer model
    private String password;
    private String name; // Combining firstname and lastname into a single name field
    private String address;
}
