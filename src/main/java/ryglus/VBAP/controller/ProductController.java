package ryglus.VBAP.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ryglus.VBAP.DTO.products.ProductCreateDto;
import ryglus.VBAP.DTO.products.ProductResponseDto;
import ryglus.VBAP.DTO.products.ProductUpdateDto;
import ryglus.VBAP.model.Product;
import ryglus.VBAP.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


        @GetMapping
        public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
            List<Product> products = productService.getAllProducts();
            List<ProductResponseDto> productResponseDtos = products.stream()
                    .map(product -> new ProductResponseDto(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.isAvailable(),
                            product.getCategory().getId()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productResponseDtos);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
            Product product = productService.getProductById(id);
            ProductResponseDto productResponseDto = new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.isAvailable(),
                    product.getCategory().getId());
            return ResponseEntity.ok(productResponseDto);
        }

        @PostMapping
        public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductCreateDto productCreateDto) {
            Product product = new Product();
            product.setName(productCreateDto.getName());
            product.setDescription(productCreateDto.getDescription());
            product.setPrice(productCreateDto.getPrice());
            product.setAvailable(productCreateDto.isAvailable());
            // Assuming you have a method in your service to get a Category by its ID
            product.setCategory(productService.getCategoryById(productCreateDto.getCategoryId()));
            Product newProduct = productService.createProduct(product);
            ProductResponseDto productResponseDto = new ProductResponseDto(
                    newProduct.getId(),
                    newProduct.getName(),
                    newProduct.getDescription(),
                    newProduct.getPrice(),
                    newProduct.isAvailable(),
                    newProduct.getCategory().getId());
            return ResponseEntity.ok(productResponseDto);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto productUpdateDto) {
            Product productDetails = new Product();
            productDetails.setName(productUpdateDto.getName());
            productDetails.setDescription(productUpdateDto.getDescription());
            productDetails.setPrice(productUpdateDto.getPrice());
            productDetails.setAvailable(productUpdateDto.isAvailable());
            // Assuming you have a method in your service to get a Category by its ID
            productDetails.setCategory(productService.getCategoryById(productUpdateDto.getCategoryId()));
            Product updatedProduct = productService.updateProduct(id, productDetails);
            ProductResponseDto productResponseDto = new ProductResponseDto(
                    updatedProduct.getId(),
                    updatedProduct.getName(),
                    updatedProduct.getDescription(),
                    updatedProduct.getPrice(),
                    updatedProduct.isAvailable(),
                    updatedProduct.getCategory().getId());
            return ResponseEntity.ok(productResponseDto);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }
    @GetMapping("/sort/name")
    public ResponseEntity<List<Product>> getProductsSortedByName() {
        return ResponseEntity.ok(productService.getProductsSortedByName());
    }

    @GetMapping("/sort/price")
    public ResponseEntity<List<Product>> getProductsSortedByPrice() {
        return ResponseEntity.ok(productService.getProductsSortedByPrice());
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }
}