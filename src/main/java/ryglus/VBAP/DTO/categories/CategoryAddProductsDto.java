package ryglus.VBAP.DTO.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddProductsDto {
    private String name;
    private List<Long> productIds;
}
