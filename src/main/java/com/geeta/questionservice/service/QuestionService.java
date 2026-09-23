package com.geeta.questionservice.service;

import com.geeta.questionservice.dao.QuestionDao;
import com.geeta.questionservice.entity.Question;
import com.geeta.questionservice.entity.QuestionDTO;
import com.geeta.questionservice.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
            try{
                return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(int questionId) {
         try{
             questionDao.deleteById(questionId);
             return new ResponseEntity<>("Question"+questionId+" deleted successfully",HttpStatus.OK);
         }
         catch(Exception e){
             e.printStackTrace();
         }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(int id,Question question) {
        try {
            if(id== question.getId()){
                questionDao.save(question);
                return new ResponseEntity<>(question.getId() + " updated successfully!",HttpStatus.OK);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("Mismatch in the id in the url and question id in the body",HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Question successfully added!",HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id: questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question: questions){
            QuestionDTO dto = new QuestionDTO();
            dto.setId(question.getId());
            dto.setQuestionTitle(question.getQuestionTitle());
            dto.setOption1(question.getOption1());
            dto.setOption2(question.getOption2());
            dto.setOption3(question.getOption3());
            dto.setOption4(question.getOption4());
            questionDTOs.add(dto);
        }
        return new ResponseEntity<>(questionDTOs,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response r : responses) {
           Question question = questionDao.findById(r.getId()).get();
            if(r.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}

