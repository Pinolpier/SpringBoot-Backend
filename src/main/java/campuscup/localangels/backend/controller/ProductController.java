package campuscup.localangels.backend.controller;

import campuscup.localangels.backend.exception.ResourceNotFoundException;
import campuscup.localangels.backend.model.Product;
import campuscup.localangels.backend.repository.MerchantRepository;
import campuscup.localangels.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @PostMapping("/merchant/{merchantId}/products")
    public Product addProduct(@PathVariable Long merchantId, @Valid @RequestBody Product product) {
        return merchantRepository.findById(merchantId).map(merchant -> {
            product.setMerchant(merchant);
            return productRepository.save(product);
        }).orElseThrow(() -> new ResourceNotFoundException("Merchant with id + " + merchantId + " + not found!"));
    }

    @GetMapping("/merchant/{merchantId}/products")
    public List<Product> getProductsByMerchant(@PathVariable Long merchantId) {
        return productRepository.findByMerchantId(merchantId);
    }

    @GetMapping("/products") // Aufruf mit ?merchantId=...&type=...&name=...
    public List<Product> getProductsWithFilter(@RequestParam(required = false) Long merchantId,
                                               @RequestParam(required = false) String type,
                                               @RequestParam(required = false) String name) {
        return productRepository.findProductsByFilter(merchantId, type, name);
    }

}