package pl.krakow.uek.ceneoReviewsApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.repository.ProductRepository;
import pl.krakow.uek.ceneoReviewsApp.repository.ReviewRepository;

import java.util.Optional;

@Service
public class CeneoService {
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public CeneoService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void delete(long id) {
        deleteReviews(id);
        productRepository.delete(id);
    }

    @Transactional
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Transactional
    public void deleteReviews(long id) {
        Optional<Product> optional = productRepository.findByProductid(id);
        optional.ifPresent(product -> {
            product.getReviews().forEach(review -> {
                review.getPros().clear();
                review.getCons().clear();
            });
            product.getReviews().clear();
        });
    }

    public long getProductRecordsNumber() {
        return productRepository.count();
    }

    public long getReviewRecordsNumber() {
        return reviewRepository.count();
    }
}
