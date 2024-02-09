package ryglus.VBAP.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterResponseDto {
    private Long id; // Assuming id is needed in the response
    private String name; // Assuming name is needed in the response
    private String address; // Assuming address is needed in the response
    private String username; // Assuming email is needed in the response

}
