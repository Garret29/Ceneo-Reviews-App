package pl.krakow.uek.ceneoReviewsApp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Product implements Serializable{

    @Id
    private long productid;

    @Lob
    @Column
    private String additionalInfo;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String type;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, mappedBy = "reviewPrimaryKey.product")
//    @JoinColumn(name = "productid", referencedColumnName = "productid")
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


    public String getModel() {
        return model;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
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

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return productid == product.productid;
    }

    @Override
    public int hashCode() {
        return (int) (productid ^ (productid >>> 32));
    }
}
