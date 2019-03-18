package pl.krakow.uek.ceneoReviewsApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.service.CeneoService;

import java.util.Hashtable;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final CeneoService ceneoService;

    @Autowired
    public ProductController(CeneoService ceneoService) {
        this.ceneoService = ceneoService;
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody Product p) {
        p.getReviews().forEach(review -> review.setProduct(p));

        ceneoService.save(p);

        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", ceneoService.getProductRecordsNumber());
        responseBody.put("newreviews", ceneoService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);

    }

    @PostMapping(value = "/{productId}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        ceneoService.delete(productId);
        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", ceneoService.getProductRecordsNumber());
        responseBody.put("newreviews", ceneoService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/{productId}/reviews/delete")
    public ResponseEntity<?> deleteProductReviews(@PathVariable Long productId) {
        ceneoService.deleteReviews(productId);
        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", ceneoService.getProductRecordsNumber());
        responseBody.put("newreviews", ceneoService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/delete-all")
    public ResponseEntity<?> deleteAllProducts() {
        ceneoService.deleteAll();
        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", ceneoService.getProductRecordsNumber());
        responseBody.put("newreviews", ceneoService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);
    }
}
