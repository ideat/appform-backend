package com.mindware.appform.entity.netbank.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DataFormDto {

    private Integer codeClient;  //gbagecage

    private String names; //gbdacnomb

    private String lastName; //gbdacap1

    private String motherLastName; //gbdacape2

    private String marriedLastName; //gbdacape3

    private String idCard; //gbagendid

    private String officeName; //adagndesc

    private String product; //catcadesc

    private String gender; //gbcondesc -> gbcon1.gbconpfij = 2 and gbcon1.gbconcorr = gbagesexo

    private String typeDocument; // gbcondes -> gbcon2.gbconpfij = 4 and gbcon2.gbconcorr = gbagetdid

    private String civilStatus; // gbcondesc -> gbcon3.gbconpfij = 3 and gbcon3.gbconcorr = gbageeciv

    private Date expiredDate; //  gbdocfvid

    private String currency; //gbcondesc -> gbcon5.gbconpfij = 10 and gbcon5.gbconcorr = camcacmon --para ahorro

    private Date bornDate; // gbagefnac

    private String addressHome;// gbdirdire -> gbdirtdir = 1

    private String profession; // gbcondesc -> gbcon6.gbconpfij = 47 and gbcon6.gbconcorr = gbageprof

    private Double incomeMountly; // round(decode(gblabcmon,1,gblabmont,2,gblabmont*gblabtcam),2)

    private String province; // gbprvdesc

    private String departament; // gbdptdesc

    private String country; // gbpaidesc

    private String zone; // gbzondesc

    private String cellphone; // gbdaccelu

    private String homePhone; // gbagetlfd

    private String activity1; // gbageact1

    private String activity2; // gbageact2

    private String reasonOpeningAccount; // cacondesc -> cadacmoti = caconcorr and caconpref = 30

    private String fullNameSpouse;

    private String activitySpouse;

    private Integer codeSpouse; //gbdaccony

    private Date openingDate;

    private String city;
//    public String getExtension(){
//        String ex="";
//        if(typeDocument.trim().equals("CARNET DE IDENTIDAD")){
//            ex = this.idCard.substring(idCard.trim().length()-2, idCard.trim().length());
//        }
//        return ex;
//    }
//
//    public String getNumberIdCard(){
//        String number="";
//        if(typeDocument.trim().equals("CARNET DE IDENTIDAD")){
//            number = this.idCard.substring(0,idCard.trim().length()-2);
//        }
//        return number;
//    }
}