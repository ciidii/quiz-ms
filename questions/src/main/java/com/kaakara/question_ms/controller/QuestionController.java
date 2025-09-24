package com.kaakara.question_ms.controller;


import com.kaakara.question_ms.model.Question;
import com.kaakara.question_ms.model.QuestionWrapper;
import com.kaakara.question_ms.model.Response;
import com.kaakara.question_ms.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return this.questionService.getAllQuestionsForQuiz(categoryName, numQuestions);
    }

    @PostMapping("fetch-question")
    public ResponseEntity<List<QuestionWrapper>> fetchQuestions(@RequestBody List<Integer> questionIds) {
        return this.questionService.fetchQuestions(questionIds);
    }
    @PostMapping("score")
    public ResponseEntity<Integer> computeScore(@RequestBody List<Response> responses) {
        return questionService.computeScore(responses);
    }
}
