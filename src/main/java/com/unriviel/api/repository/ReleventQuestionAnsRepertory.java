package com.unriviel.api.repository;

import com.unriviel.api.model.RelevantQsAns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleventQuestionAnsRepertory extends JpaRepository<RelevantQsAns, Long> {

}
