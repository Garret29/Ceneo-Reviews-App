package pl.krakow.uek.ceneoReviewsApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krakow.uek.ceneoReviewsApp.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByProductid(long id);
    Optional<Product> findByModel(String model);
}
