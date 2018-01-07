package pl.krakow.uek.ceneoReviewsApp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.krakow.uek.ceneoReviewsApp.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {

}
