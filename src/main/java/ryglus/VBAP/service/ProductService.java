package ryglus.VBAP.service;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ryglus.VBAP.model.Category;
import ryglus.VBAP.model.Product;
import ryglus.VBAP.repository.CategoryRepository;
import ryglus.VBAP.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) { // Modify this line
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository; // Add this line
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        // add other fields that you want to update
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found")); // Modify this line
        return category.getProducts();
    }
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    public List<Product> getProductsSortedByName() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<Product> getProductsSortedByPrice() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }
}