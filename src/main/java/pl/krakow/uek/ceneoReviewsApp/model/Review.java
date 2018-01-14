package pl.krakow.uek.ceneoReviewsApp.model;

import pl.krakow.uek.ceneoReviewsApp.model.primaryKeys.ReviewPrimaryKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Review implements Serializable {

    @EmbeddedId
    private
    ReviewPrimaryKey reviewPrimaryKey;

    @ElementCollection
    @CollectionTable(joinColumns = {@JoinColumn(name = "productid"), @JoinColumn(name = "author")})
    @Column
    private
    List<String> pros;

    @ElementCollection
    @CollectionTable(joinColumns = {@JoinColumn(name = "productid"), @JoinColumn(name = "author")})
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

    public ReviewPrimaryKey getReviewPrimaryKey() {
        return reviewPrimaryKey;
    }

    public void setReviewPrimaryKey(ReviewPrimaryKey reviewPrimaryKey) {
        this.reviewPrimaryKey = reviewPrimaryKey;
    }

    public void setProduct(Product product) {
        getReviewPrimaryKey().setProduct(product);
    }

    public Product getProduct(){
        return getReviewPrimaryKey().getProduct();
    }

    public void setAuthor(String author){
        getReviewPrimaryKey().setAuthor(author);
    }

    public String getAuthor(){
        return getReviewPrimaryKey().getAuthor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        return reviewPrimaryKey.equals(review.reviewPrimaryKey);
    }

    @Override
    public int hashCode() {
        return reviewPrimaryKey.hashCode();
    }
}
