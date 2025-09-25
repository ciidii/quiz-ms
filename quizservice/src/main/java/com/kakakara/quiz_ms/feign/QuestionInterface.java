package com.kakakara.quiz_ms.feign;

import com.kakakara.quiz_ms.model.Question;
import com.kakakara.quiz_ms.model.QuestionWrapper;
import com.kakakara.quiz_ms.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTION-MS")
public interface QuestionInterface {
    @GetMapping("question/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions();

    @GetMapping("question/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category);

    @PostMapping("question/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question);

    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);

    @PostMapping("question/fetch-question")
    public ResponseEntity<List<QuestionWrapper>> fetchQuestions(@RequestBody List<Integer> questionIds);

    @PostMapping("question/score")
    public ResponseEntity<Integer> computeScore(@RequestBody List<Response> responses);
}
