package com.quiz.layoutPDF.Repository;

import com.quiz.layoutPDF.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q.answer FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.questionNum")
    List<String> findAnswersByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT q.marks FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.questionNum")
    List<Long> findMarksByQuizId(@Param("quizId") Long quizId);
}