package com.unriviel.api.repository;

import com.unriviel.api.model.RelevantQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelevantQuestionsRepository extends JpaRepository<RelevantQuestion, Long> {

    Optional<RelevantQuestion> findByQuestion(String question);
}
