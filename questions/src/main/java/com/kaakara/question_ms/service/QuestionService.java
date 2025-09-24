package com.kaakara.question_ms.service;


import com.kaakara.question_ms.dao.QuestionDao;
import com.kaakara.question_ms.model.Question;
import com.kaakara.question_ms.model.QuestionWrapper;
import com.kaakara.question_ms.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategoryIgnoreCase(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getAllQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);

        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> fetchQuestions(List<Integer> questionIds) {
        List<Question> questions = questionDao.findAllById(questionIds);
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        questions.forEach(question -> {

            questionWrappers.add(new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()));
        });
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> computeScore(List<Response> responses) {
        int score = (int) responses.stream()
                .filter(response -> questionDao.findById(response.getId())
                        .map(q -> q.getRightAnswer().trim().equals(response.getResponse()))
                        .orElse(false))
                .count();
        return ResponseEntity.ok(score);
    }

}