package com.unriviel.api.service;

import com.unriviel.api.dto.Response;
import com.unriviel.api.model.uploadconfig.UploadConfig;

public interface UploadConfigService {
     Response create(UploadConfig config);
     Response findById(long id);
}
