package com.kakakara.quiz_ms.service;

import com.kakakara.quiz_ms.dao.QuizDao;
import com.kakakara.quiz_ms.feign.QuestionInterface;
import com.kakakara.quiz_ms.model.Question;
import com.kakakara.quiz_ms.model.QuestionWrapper;
import com.kakakara.quiz_ms.model.Quiz;
import com.kakakara.quiz_ms.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionInterface questionInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questionIds = questionInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionIds);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionsFromDB = quiz.getQuestions();
        List<QuestionWrapper> questionsForUser = this.questionInterface.fetchQuestions(questionsFromDB).getBody();
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        return this.questionInterface.computeScore(responses);
    }
}
