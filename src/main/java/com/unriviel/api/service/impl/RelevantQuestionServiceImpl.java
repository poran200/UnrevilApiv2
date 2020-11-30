package com.unriviel.api.service.impl;
import com.unriviel.api.dto.RelevenetQSDto;
import com.unriviel.api.dto.Response;
import com.unriviel.api.model.RelevantQuestion;
import com.unriviel.api.repository.RelevantQuestionsRepository;
import com.unriviel.api.service.RelevantQuestionService;
import com.unriviel.api.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.unriviel.api.util.ResponseBuilder.getFailureResponse;
import static com.unriviel.api.util.ResponseBuilder.getSuccessResponse;


@Service
public class RelevantQuestionServiceImpl implements RelevantQuestionService {
    private final RelevantQuestionsRepository relevantQuestionsRepository;
    private final ModelMapper modelMapper;
    public RelevantQuestionServiceImpl(RelevantQuestionsRepository relevantQuestionsRepository, ModelMapper modelMapper) {
        this.relevantQuestionsRepository = relevantQuestionsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response create(RelevenetQSDto dto) {
        RelevantQuestion question = modelMapper.map(dto, RelevantQuestion.class);
        RelevantQuestion saveQs = relevantQuestionsRepository.save(question);
        if (saveQs == null) return getFailureResponse(HttpStatus.BAD_REQUEST,"qustion not created");
        return getSuccessResponse(HttpStatus.OK,"Qusetion created",modelMapper.map(saveQs,RelevenetQSDto.class));
    }


    @Override
    public Response getAllQuestions() {
      List<RelevantQuestion> questions = relevantQuestionsRepository.findAll();
      questions.forEach(question-> modelMapper.map(question,RelevenetQSDto.class));
      return ResponseBuilder.getSuccessResponseList(HttpStatus.OK,"all rlevent  question",questions,questions.size());
    }

    @Override
    public Response findById(long id) {
        Optional<RelevantQuestion> optional = relevantQuestionsRepository.findById(id);
        if (optional.isPresent()) return getSuccessResponse(HttpStatus.OK,"found question",modelMapper.map(optional.get(),RelevenetQSDto.class));
        return getFailureResponse(HttpStatus.NOT_FOUND,"Question not found");

    }
}
