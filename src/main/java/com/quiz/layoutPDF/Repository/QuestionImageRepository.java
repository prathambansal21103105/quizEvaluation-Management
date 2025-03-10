package com.quiz.layoutPDF.Repository;
import com.quiz.layoutPDF.models.QuestionImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionImageRepository extends MongoRepository<QuestionImage, String> {
}