package uk.ac.westminster.products_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getProductById(id).orElse(null);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProduct();
    }


    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }


    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.findByName(name);
    }


}
