package ryglus.VBAP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ryglus.VBAP.DTO.categories.CategoryAddProductsDto;
import ryglus.VBAP.DTO.categories.CategoryCreateDto;
import ryglus.VBAP.model.Category;
import ryglus.VBAP.model.Product;
import ryglus.VBAP.service.CategoryService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(newCategory);
    }
    @PostMapping("/addProducts")
    public ResponseEntity<Category> addProductsToCategory(@RequestBody CategoryAddProductsDto categoryAddProductsDto) {
        Category category = categoryService.addProductsToCategory(categoryAddProductsDto.getName(), categoryAddProductsDto.getProductIds());
        return ResponseEntity.ok(category);
    }
    @GetMapping("{categoryIdOrName}/products")
    public ResponseEntity<List<Product>> getProductsFromCategory(@PathVariable String categoryIdOrName) {
        List<Product> products = categoryService.getProductsFromCategory(categoryIdOrName);
        return ResponseEntity.ok(products);
    }
}