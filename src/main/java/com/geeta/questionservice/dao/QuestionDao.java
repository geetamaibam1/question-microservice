package com.geeta.questionservice.dao;

import com.geeta.questionservice.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
    public List<Question> findByCategory(String category);
    @Query(value="select q.id from questions q where q.category=:category order by DBMS_RANDOM.VALUE FETCH FIRST :numQ rows only", nativeQuery=true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
