package com.mindware.appform.entity;

import lombok.Data;

@Data
public class VerifyIdCard {
    private String id;

    private String createDate;

    private String idCardForVerification; //json DataIdCard
}
