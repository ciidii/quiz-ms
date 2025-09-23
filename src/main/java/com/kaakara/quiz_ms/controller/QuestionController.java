package com.kaakara.quiz_ms.controller;

import com.kaakara.quiz_ms.entities.Question;
import com.kaakara.quiz_ms.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questions")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @GetMapping("allQuestions")
    public List<Question> allQuestions() {
        return this.questionService.getQuestions();
    }
}
