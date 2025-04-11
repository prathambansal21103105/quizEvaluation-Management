package com.quiz.layoutPDF.Repository;

import com.quiz.layoutPDF.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCourseCode(String courseCode);

    @Query("SELECT q FROM Quiz q ORDER BY q.id DESC")
    List<Quiz> findAllQuizes();

    @Query("SELECT q FROM Quiz q WHERE LOWER(q.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(q.courseCode) = LOWER(:searchTerm) ORDER BY q.id DESC")
    List<Quiz> findByTitleContainingIgnoreCase(@Param("searchTerm") String searchTerm);

    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questions WHERE q.id = :id")
    Quiz findByIdWithQuestions(@Param("id") Long id);
}