package ryglus.VBAP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ryglus.VBAP.model.Category;
import ryglus.VBAP.model.Product;
import ryglus.VBAP.repository.CategoryRepository;
import ryglus.VBAP.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addProductsToCategory(String categoryIdOrName, List<Long> productIds) {
        Optional<Category> categoryOptional;
        try {
            Long categoryId = Long.parseLong(categoryIdOrName);
            categoryOptional = categoryRepository.findById(categoryId);
        } catch (NumberFormatException e) {
            categoryOptional = categoryRepository.findByName(categoryIdOrName);
        }

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<Product> productsToAdd = productRepository.findAllById(productIds);
            category.getProducts().addAll(productsToAdd);
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found");
        }
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
    public List<Product> getProductsFromCategory(String categoryIdOrName) {
        Optional<Category> categoryOptional;
        try {
            Long categoryId = Long.parseLong(categoryIdOrName);
            categoryOptional = categoryRepository.findById(categoryId);
        } catch (NumberFormatException e) {
            categoryOptional = categoryRepository.findByName(categoryIdOrName);
        }

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getProducts();
        } else {
            throw new RuntimeException("Category not found");
        }
    }
}