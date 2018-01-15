package pl.krakow.uek.ceneoReviewsApp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq")
    private Long reviewid;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "reviewid"))
    @Column
    private
    List<String> pros;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "reviewid"))
    @Column
    private
    List<String> cons;

    @Column
    private Double rating;

    @Lob
    @Column
    private String summary;

    @Column
    private String recommendation;

    @Column
    private Integer upvotes;

    @Column
    private Integer downvotes;

    @ManyToOne
    @JoinColumn(name = "productid", referencedColumnName = "productid")
    private Product product;

    @Column
    private String author;

    public Review() {
    }

    public List<String> getPros() {
        return pros;
    }

    public void setPros(List<String> pros) {
        this.pros = pros;
    }

    public List<String> getCons() {
        return cons;
    }

    public void setCons(List<String> cons) {
        this.cons = cons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommended) {
        recommendation = recommended;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public Long getReviewid() {
        return reviewid;
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
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        if (pros != null ? !pros.equals(review.pros) : review.pros != null) return false;
        if (cons != null ? !cons.equals(review.cons) : review.cons != null) return false;
        if (rating != null ? !rating.equals(review.rating) : review.rating != null) return false;
        if (summary != null ? !summary.equals(review.summary) : review.summary != null) return false;
        if (recommendation != null ? !recommendation.equals(review.recommendation) : review.recommendation != null)
            return false;
        if (upvotes != null ? !upvotes.equals(review.upvotes) : review.upvotes != null) return false;
        if (downvotes != null ? !downvotes.equals(review.downvotes) : review.downvotes != null) return false;
        if (product != null ? !product.equals(review.product) : review.product != null) return false;
        return author != null ? author.equals(review.author) : review.author == null;
    }

    @Override
    public int hashCode() {
        int result = pros != null ? pros.hashCode() : 0;
        result = 31 * result + (cons != null ? cons.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (recommendation != null ? recommendation.hashCode() : 0);
        result = 31 * result + (upvotes != null ? upvotes.hashCode() : 0);
        result = 31 * result + (downvotes != null ? downvotes.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}
