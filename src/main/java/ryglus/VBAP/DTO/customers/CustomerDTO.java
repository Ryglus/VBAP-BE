package ryglus.VBAP.DTO.customers;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String username;
}

