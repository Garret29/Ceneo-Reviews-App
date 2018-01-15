package pl.krakow.uek.ceneoReviewsApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.service.DeleteService;
import pl.krakow.uek.ceneoReviewsApp.service.LoadService;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final LoadService loadService;
    private final DeleteService deleteService;
    private final AtomicInteger anonymousCount = new AtomicInteger(0);

    @Autowired
    public ProductController(LoadService loadService, DeleteService deleteService) {
        this.loadService = loadService;
        this.deleteService = deleteService;
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody Product p) {
        p.getReviews().forEach(review -> review.setProduct(p));

        loadService.load(p);

        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", loadService.getProductRecordsNumber());
        responseBody.put("newreviews", loadService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);

    }

    @PostMapping(value = "/{productId}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        deleteService.delete(productId);
        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", loadService.getProductRecordsNumber());
        responseBody.put("newreviews", loadService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/{productId}/reviews/delete")
    public ResponseEntity<?> deleteProductReviews(@PathVariable Long productId) {
        deleteService.deleteReviews(productId);
        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", loadService.getProductRecordsNumber());
        responseBody.put("newreviews", loadService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/delete-all")
    public ResponseEntity<?> deleteAllProducts() {
        deleteService.deleteAll();
        Hashtable<String, Long> responseBody = new Hashtable<>();
        responseBody.put("newproducts", loadService.getProductRecordsNumber());
        responseBody.put("newreviews", loadService.getReviewRecordsNumber());

        return ResponseEntity.ok().body(responseBody);
    }
}
