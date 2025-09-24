package com.kaakara.quiz_ms.dao;

import com.kaakara.quiz_ms.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
