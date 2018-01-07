package pl.krakow.uek.ceneoReviewsApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krakow.uek.ceneoReviewsApp.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findById(long id);
}
