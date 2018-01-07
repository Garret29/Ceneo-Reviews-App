package pl.krakow.uek.ceneoReviewsApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.repository.ProductRepository;
import pl.krakow.uek.ceneoReviewsApp.service.LoadService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/reviews")
public class ProductController {

    private final LoadService loadService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(LoadService loadService, ProductRepository productRepository) {
        this.loadService = loadService;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody Product p) {
        p.getReviews().forEach(review -> review.setProduct(p));

        Optional<Product> optional = productRepository.findByModel(p.getModel());

        optional.ifPresent(product -> {
            p.getReviews().forEach(review -> {
                if (!product.getReviews().contains(review)) {
                    product.getReviews().add(review);
                }
            });
            productRepository.save(product);
        });
        if (!optional.isPresent()){
            productRepository.save(p);
        }

        return ResponseEntity.ok().build();
    }

}
