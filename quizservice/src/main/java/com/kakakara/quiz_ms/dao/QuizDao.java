package com.kakakara.quiz_ms.dao;

import com.kakakara.quiz_ms.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
