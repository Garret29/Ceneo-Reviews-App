package pl.krakow.uek.ceneoReviewsApp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.krakow.uek.ceneoReviewsApp.model.Product;
import pl.krakow.uek.ceneoReviewsApp.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    void deleteAllByReviewPrimaryKey_Product(Product product);
    void deleteReviewsByReviewPrimaryKey_Product(Product product);
}
