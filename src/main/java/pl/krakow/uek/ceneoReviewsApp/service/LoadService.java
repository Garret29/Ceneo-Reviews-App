package pl.krakow.uek.ceneoReviewsApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.repository.ProductRepository;
import pl.krakow.uek.ceneoReviewsApp.repository.ReviewRepository;

import java.util.Optional;

@Service
public class LoadService {
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public LoadService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void load(Product product) {
//        Optional<Product> optional = productRepository.findByProductid(product.getProductid());
//
//        optional.ifPresent(product1 -> {
//            product1.getReviews().forEach(review -> {
//                review.getPros().clear();
//                review.getCons().clear();
//            });
//            product1.getReviews().clear();
//            product1.setReviews(product.getReviews());
//            productRepository.save(product1);
//        });
//        if (!optional.isPresent()) {
        productRepository.save(product);
//        }
    }

    public long getProductRecordsNumber() {
        return productRepository.count();
    }

    public long getReviewRecordsNumber() {
        return reviewRepository.count();
    }
}
