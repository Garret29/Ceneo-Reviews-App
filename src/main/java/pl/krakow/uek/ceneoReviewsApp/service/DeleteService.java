package pl.krakow.uek.ceneoReviewsApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.model.Review;
import pl.krakow.uek.ceneoReviewsApp.repository.ProductRepository;
import pl.krakow.uek.ceneoReviewsApp.repository.ReviewRepository;

import java.util.Iterator;
import java.util.Optional;

@Service
public class DeleteService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final LoadService loadService;

    @Autowired
    public DeleteService(ProductRepository productRepository, ReviewRepository reviewRepository, LoadService loadService) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.loadService = loadService;
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

}
