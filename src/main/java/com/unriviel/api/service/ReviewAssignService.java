package com.unriviel.api.service;

import com.unriviel.api.dto.Response;

public interface ReviewAssignService {
    Response assign(String videoId,String reviewerEmail);
}
