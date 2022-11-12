package com.mindware.appform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FormToSelectReportDto {
    private String id;

    private Integer idClient;

    private String idAccount;

    private String account;

    private String cardNumber;

    private String nameTypeForm;

    private String categoryTypeForm;
}
