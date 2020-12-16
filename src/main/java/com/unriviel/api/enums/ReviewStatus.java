package com.unriviel.api.enums;

import java.io.Serializable;

public enum ReviewStatus implements Serializable {
    TO_BE_REVIEWED,
    IN_REVIEW,
    REVIEWED,
    APPROVED,
    REJECTED,
}
