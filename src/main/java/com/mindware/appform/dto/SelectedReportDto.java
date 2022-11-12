package com.mindware.appform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelectedReportDto {
//    FormDtoReport
    private List<FormsDtoReport> formsDtoReports;

//    FormDigitalBankDtoreport
    private List<FormDigitalBankDtoReport> formDigitalBankDtoReports;
//    FormDebitCardDtoReport
    private List<FormDebitCardDtoReport> formDebitCardDtoReports;

    private String officeName;

    private String login;
}
