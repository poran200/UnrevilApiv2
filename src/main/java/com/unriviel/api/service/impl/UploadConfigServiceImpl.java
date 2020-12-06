package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.model.uploadconfig.UploadConfig;
import com.unriviel.api.repository.UploadConfigRepository;
import com.unriviel.api.service.UploadConfigService;
import com.unriviel.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UploadConfigServiceImpl implements UploadConfigService {
    private UploadConfigRepository uploadConfigRepository;
    @Override
    public Response create(UploadConfig config) {
        var save = uploadConfigRepository.save(config);
        if (save != null){
            log.info("configuration created [id] = "+save.getId());
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,"Configuration created! Id  ="+save.getId(), null);
        }
        log.info("configuration not crated some issue happen");
        return ResponseBuilder.getInternalServerError();

    }

    @Override
    public Response findById(long id) {
        var optional = uploadConfigRepository.findById(id);
        if (optional.isPresent()){
            return  ResponseBuilder.getSuccessResponse(HttpStatus.OK,"configuration found",optional.get());
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Configuration not fond! [id] = "+id);

    }
}
