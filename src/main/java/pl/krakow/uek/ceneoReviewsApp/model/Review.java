package pl.krakow.uek.ceneoReviewsApp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq")
    private long id;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "review_id"))
    @Column
    private
    List<String> pros;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "review_id"))
    @Column
    private
    List<String> cons;

    @Column
    private String author;

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

    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    public Review(List<String> pros, List<String> cons, String author, Double rating, String summary, String recommendation, Integer upvotes, Integer downvotes, Product product) {
        this.pros = pros;
        this.cons = cons;
        this.author = author;
        this.rating = rating;
        this.summary = summary;
        this.recommendation = recommendation;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.product = product;
    }

    public Review() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        return author.equals(review.author) && product.equals(review.product);
    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}
