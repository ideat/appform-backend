package com.mindware.appform.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FormVerifyIdCardDtoReport {
    private String idCard;

    private String fullName;

    private String extension;

    private String city;

    private Date creationDate;
}
