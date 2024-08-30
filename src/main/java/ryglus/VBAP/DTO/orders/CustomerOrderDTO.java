package ryglus.VBAP.DTO.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ryglus.VBAP.DTO.products.ProductDTO;

import java.util.Date;
import java.util.List;

// CustomerOrder DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDTO {
    private Long id;
    private Date orderDate;
    private String status;
    private List<ProductDTO> products; // Include only product information needed
}
