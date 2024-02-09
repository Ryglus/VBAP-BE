package ryglus.VBAP.DTO.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {
    private String name;
    private String description;
    private double price;
    private boolean available;
    private Long categoryId;
}