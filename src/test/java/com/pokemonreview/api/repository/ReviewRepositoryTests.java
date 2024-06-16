package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    public ReviewRepository reviewRepository;


    // Dependency Injection through Constructor.
    @Autowired
    public ReviewRepositoryTests(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview() {
        // Arrange
        Review review = Review.builder().title("title")
                .content("This is the content").stars(5).build();

        // Act
        Review savedReview = reviewRepository.save(review);

        // Assert.
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnAllReviews() {
        //Arrange
        Review review1 = Review.builder().title("review1").content("This is the content")
                .stars(5).build();
        Review review2 = Review.builder().title("review2").content("This is the content")
                .stars(4).build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        //Act
        List<Review>reviews = reviewRepository.findAll();

        //Assert
        Assertions.assertThat(reviews).isNotNull();
        Assertions.assertThat(reviews).size().isEqualTo(2);
    }

    // Test if we could find a review based on the id.
    @Test
    public void ReviewRepository_findById_ReturnSavedReview() {
        Review review = Review.builder().title("title").content("This is the content").stars(5).build();

        reviewRepository.save(review);
        Review foundReview = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(foundReview).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder().title("title").content("This is the content").stars(5).build();

        reviewRepository.save(review);

        Review foundReview = reviewRepository.findById(review.getId()).get();
        foundReview.setTitle("This is a new title");
        foundReview.setContent("This is some new content");
        foundReview.setStars(5);

        Review updatedPokemon = reviewRepository.save(foundReview);

        Assertions.assertThat(updatedPokemon).isNotNull();
        Assertions.assertThat(updatedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_ReviewDelete_ReturnReviewisEmpty() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());

        Assertions.assertThat(reviewReturn).isEmpty();
    }
}
