package ryglus.VBAP.DTO.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Date orderDate;
    private String status;
    private List<Long> productIds;  // Only expose product IDs
}
