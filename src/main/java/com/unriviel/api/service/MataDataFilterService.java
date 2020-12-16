package com.unriviel.api.service;

import com.unriviel.api.dto.MetaDataFilterRequest;
import com.unriviel.api.dto.Response;
import org.springframework.data.domain.Pageable;

public interface MataDataFilterService {
    Response filterMetaDataByTiterOrEmailOrNameOrFullName(MetaDataFilterRequest request, Pageable pageable);
}
