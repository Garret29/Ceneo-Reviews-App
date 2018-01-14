package pl.krakow.uek.ceneoReviewsApp.model.primaryKeys;

import pl.krakow.uek.ceneoReviewsApp.model.Product;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ReviewPrimaryKey implements Serializable{

    @ManyToOne
    @JoinColumn(name = "productid", referencedColumnName = "productid")
    private Product product;

    private String author;


    public ReviewPrimaryKey() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewPrimaryKey)) return false;

        ReviewPrimaryKey that = (ReviewPrimaryKey) o;

        return product.equals(that.product) && author.equals(that.author);
    }

    @Override
    public int hashCode() {
        int result = product.hashCode();
        result = 31 * result + author.hashCode();
        return result;
    }
}
