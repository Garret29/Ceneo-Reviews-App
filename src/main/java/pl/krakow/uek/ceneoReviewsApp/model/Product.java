package pl.krakow.uek.ceneoReviewsApp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq")
    private long id;

    @Lob
    @Column
    private String additionalInfo;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String type;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Product(String additionalInfo, String brand, String model, String type, List<Review> reviews) {
        this.additionalInfo = additionalInfo;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.reviews = reviews;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return model.equals(product.model);
    }

    @Override
    public int hashCode() {
        return model.hashCode();
    }
}
