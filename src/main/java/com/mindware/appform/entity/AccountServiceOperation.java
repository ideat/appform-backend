package com.mindware.appform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AccountServiceOperation {
    private String id;

    private String createDate;

    private String account;

    private String services; //json parameter

    private String reasonOpening;

    private Double extensionAmount;

    private Double decreaseAmount;
}
