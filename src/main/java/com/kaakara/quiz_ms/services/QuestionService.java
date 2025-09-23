package com.kaakara.quiz_ms.services;

import com.kaakara.quiz_ms.entities.Question;
import com.kaakara.quiz_ms.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
  public   List<Question> getQuestions() {
        return this.questionRepository.findAll();
    }
}
