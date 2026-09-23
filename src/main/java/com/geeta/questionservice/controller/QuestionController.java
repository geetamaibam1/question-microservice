package com.geeta.questionservice.controller;

import com.geeta.questionservice.entity.Question;
import com.geeta.questionservice.entity.QuestionDTO;
import com.geeta.questionservice.entity.Response;
import com.geeta.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable("id") int id,@RequestBody Question question){
        return questionService.updateQuestion(id,question);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    //generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam int numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }
    //getQuestions(questionId)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }
    //getScore
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
       return questionService.getScore(responses);
    }
}
