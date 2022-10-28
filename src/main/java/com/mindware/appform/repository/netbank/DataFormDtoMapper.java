package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.DataFormDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataFormDtoMapper {

    @Select(" select gbdaccage as code_client, gbdacnomb as names, gbdacape1 as last_name, " +
            " gbdacape2 as mother_last_name, gbdacape3 as married_last_name, " +
            " trim(gbagendid) as idcard, gbofides1 as office_name, catcadesc as product, " +
            " gbcon1.gbcondesc as gender, gbcon2.gbcondesc as type_document, gbcon3.gbcondesc as civil_status, " +
            " gbdocfvid as expired_date, gbcon5.gbcondesc as currency, gbagefnac as born_date, " +
            " gbdirdire as address_home, gbprfdesc as profession, " +
            " 0.0 as income_mountly, " +
            " gbprvdesc as province, gbdptdesc as departament, gbpaidesc as country, gbzondesc as zone, " +
            " gbdaccelu as cellphone, gbagetlfd as home_phone, " +
            " gbageciiu as activity1, '' as activity2, cacondesc as reason_opening_account, gbrelcage as code_spouse," +
            " camcafapt as opening_date, gbciudesc as city, gbdacmail as email, gbagenomb as full_name_client, " +
            " cafirncta as account " +
            " from gbdac " +
            " inner join gbage on gbagecage = gbdaccage " +
            " inner join cafir on cafircage = gbagecage " +
            " inner join camca on camcancta = cafirncta  " +
            " inner join catca on catcatpca = camcatpca  " +
            " inner join gbofi on gbofinofi = camcaagen  " +
            " left join gbcon  gbcon1 on (gbcon1.gbconpfij = 2 and gbcon1.gbconcorr = gbagesexo)  " +
            " left join gbcon gbcon2 on (gbcon2.gbconpfij = 4 and gbcon2.gbconcorr = gbagetdid)  " +
            " left join gbcon gbcon3 on (gbcon3.gbconpfij = 3 and gbcon3.gbconcorr = gbageeciv)  " +
            " left join gbcon gbcon5 on (gbcon5.gbconpfij = 10 and gbcon5.gbconcorr = camcacmon)  " +
            " left join gbprf on gbprfprof = gbageprof  " +
            " left join gbrel on gbrelcagr = gbagecage and gbreltrel = 1 " +
            " inner join gbdoc on gbdoccage = gbagecage  " +
            " inner join gbdir on gbdircage = gbagecage and gbdirtdir = 1 and gbdirmrcb = 0 " +
            " inner join gbprv on gbdircprv = gbprvcprv " +
            " inner join gbciu on gbciuciud = gbdirciud  " +
            " inner join gbpai on gbpaipais = gbdirpais " +
            " inner join gbdpt on gbdptdpto = gbdirdpto  " +
            " inner join gbzon on gbzonzona = gbdirzona " +
            " inner join cadac on cadacncta = camcancta  " +
            " inner join cacon on cadacmoti = caconcorr and caconpref = 30  " +
            " where gbagecage = #{cage}  " +
            " and cafirncta = #{account}  " +
            " and camcastat = 1 " +
            " and cafirstat = 0 ")
    DataFormDto findDataFormForSavingBank(@Param("cage") Integer cage, @Param("account") String account);

    @Select(" select gbdaccage as code_client, gbdacnomb as names, gbdacape1 as last_name, " +
            " gbdacape2 as mother_last_name, gbdacape3 as married_last_name, " +
            " trim(gbagendid) as idcard, gbofides1 as office_name, pfhtsdesc as product, " +
            " gbcon1.gbcondesc as gender, gbcon2.gbcondesc as type_document, gbcon3.gbcondesc as civil_status, " +
            " gbdocfvid as expired_date, gbcon5.gbcondesc as currency, gbagefnac as born_date, " +
            " gbdirdire as address_home, gbprfdesc as profession, " +
            " 0.0 as income_mountly, " +
            " gbprvdesc as province, gbdptdesc as departament, gbpaidesc as country, gbzondesc as zone, " +
            " gbdaccelu as cellphone, gbagetlfd as home_phone, " +
            " gbageciiu as activity1, '' as activity2, pfcondesc as reason_opening_account, gbrelcage as code_spouse," +
            " pfmdpfreg as opening_date, gbciudesc as city, gbdacmail as email, gbagenomb as full_name_client, " +
            " pfmdpndep as account " +
            " from gbdac " +
            " inner join gbage on gbagecage = gbdaccage " +
            " inner join pfmdp on gbdaccage = pfmdpcage " +
            " inner join pfhts on pfmdptdep = pfhtstdep  " +
            " inner join pftdp on pfmdptdep = pftdptdep  " +
            " inner join gbofi on gbofinofi = pfmdpagen  " +
            " left join gbcon  gbcon1 on (gbcon1.gbconpfij = 2 and gbcon1.gbconcorr = gbagesexo)  " +
            " left join gbcon gbcon2 on (gbcon2.gbconpfij = 4 and gbcon2.gbconcorr = gbagetdid)  " +
            " left join gbcon gbcon3 on (gbcon3.gbconpfij = 3 and gbcon3.gbconcorr = gbageeciv)  " +
            " left join gbcon gbcon5 on (gbcon5.gbconpfij = 10 and gbcon5.gbconcorr = pfmdpcmon)  " +
            " left join gbprf on gbprfprof = gbageprof  " +
            " left join gbrel on gbrelcagr = gbagecage and gbreltrel = 1 " +
            " inner join gbdoc on gbdoccage = gbagecage  " +
            " inner join gbdir on gbdircage = gbagecage and gbdirtdir = 1 and gbdirmrcb = 0 " +
            " inner join gbprv on gbdircprv = gbprvcprv " +
            " inner join gbciu on gbciuciud = gbdirciud  " +
            " inner join gbpai on gbpaipais = gbdirpais " +
            " inner join gbdpt on gbdptdpto = gbdirdpto  " +
            " inner join gbzon on gbzonzona = gbdirzona " +
            " left join pfven on pfvenndep = pfmdpndep and pfvenmrcb = 0 " +
            " left join pfcon on pfconpfij = 30 and pfvenmoti = pfconcorr" +
            " where gbagecage = #{cage}  " +
            " and pfmdpndep = #{account}  " +
            " and pfmdpcmon = pfhtscmon and pfmdpplzo between pfhtsplzi and pfhtsplzf " +
            " and pfmdpstat = 1 ")
    DataFormDto findDataFormForDpf(@Param("cage") Integer cage, @Param("account") String account);


    @Select(" select gbdaccage as code_client, gbdacnomb as names, gbdacape1 as last_name, " +
            " gbdacape2 as mother_last_name, gbdacape3 as married_last_name, " +
            " trim(gbagendid) as idcard, gbofides1 as office_name, pfhtsdesc as product, " +
            " gbcon1.gbcondesc as gender, gbcon2.gbcondesc as type_document, gbcon3.gbcondesc as civil_status, " +
            " gbdocfvid as expired_date, gbcon5.gbcondesc as currency, gbagefnac as born_date, " +
            " gbdirdire as address_home, gbprfdesc as profession, " +
            " 0.0 as income_mountly, " +
            " gbprvdesc as province, gbdptdesc as departament, gbpaidesc as country, gbzondesc as zone, " +
            " gbdaccelu as cellphone, gbagetlfd as home_phone, " +
            " gbageciiu as activity1, '' as activity2, pfcondesc as reason_opening_account, gbrelcage as code_spouse," +
            " pfmdpfreg as opening_date, gbciudesc as city, gbdacmail as email, gbagenomb as full_name_client, " +
            " pfmdpndep as account " +
            " from gbdac " +
            " inner join gbage on gbagecage = gbdaccage " +
            " inner join pftit on pftitcage = gbdaccage " +
            " inner join pfmdp on pfmdpndep = pftitndep " +
            " inner join pfhts on pfmdptdep = pfhtstdep  " +
            " inner join pftdp on pfmdptdep = pftdptdep  " +
            " inner join gbofi on gbofinofi = pfmdpagen  " +
            " left join gbcon  gbcon1 on (gbcon1.gbconpfij = 2 and gbcon1.gbconcorr = gbagesexo)  " +
            " left join gbcon gbcon2 on (gbcon2.gbconpfij = 4 and gbcon2.gbconcorr = gbagetdid)  " +
            " left join gbcon gbcon3 on (gbcon3.gbconpfij = 3 and gbcon3.gbconcorr = gbageeciv)  " +
            " left join gbcon gbcon5 on (gbcon5.gbconpfij = 10 and gbcon5.gbconcorr = pfmdpcmon)  " +
            " left join gbprf on gbprfprof = gbageprof  " +
            " left join gbrel on gbrelcagr = gbagecage and gbreltrel = 1 " +
            " inner join gbdoc on gbdoccage = gbagecage  " +
            " inner join gbdir on gbdircage = gbagecage and gbdirtdir = 1 and gbdirmrcb = 0 " +
            " inner join gbprv on gbdircprv = gbprvcprv " +
            " inner join gbciu on gbciuciud = gbdirciud  " +
            " inner join gbpai on gbpaipais = gbdirpais " +
            " inner join gbdpt on gbdptdpto = gbdirdpto  " +
            " inner join gbzon on gbzonzona = gbdirzona " +
            " left join pfven on pfvenndep = pfmdpndep and pfvenmrcb = 0 " +
            " left join pfcon on pfconpfij = 30 and pfvenmoti = pfconcorr" +
            " where gbagecage = #{cage}  " +
            " and pfmdpndep = #{account}  " +
            " and pfmdpcmon = pfhtscmon and pfmdpplzo between pfhtsplzi and pfhtsplzf " +
            " and pfmdpstat = 1 ")
    DataFormDto findDataFormForDpfPftit(@Param("cage") Integer cage, @Param("account") String account);



    @Select(" select distinct gbdaccage as code_client, gbdacnomb as names, gbdacape1 as last_name, " +
            " gbdacape2 as mother_last_name, gbdacape3 as married_last_name, " +
            " trim(gbagendid) as idcard, gbofides1 as office_name, catcadesc as product, " +
            " gbcon1.gbcondesc as gender, gbcon2.gbcondesc as type_document, gbcon3.gbcondesc as civil_status, " +
            " gbdocfvid as expired_date, gbcon5.gbcondesc as currency, gbagefnac as born_date, " +
            " gbdirdire as address_home, gbprfdesc as profession, " +
            " 0.0 as income_mountly, " +
            " gbprvdesc as province, gbdptdesc as departament, gbpaidesc as country, gbzondesc as zone, " +
            " gbdaccelu as cellphone, gbagetlfd as home_phone, " +
            " gbageciiu as activity1, '' as activity2, cacondesc as reason_opening_account, gbrelcage as code_spouse," +
            " camcafapt as opening_date, gbciudesc as city, gbdacmail as email, gbagenomb as full_name_client, " +
            " camcancta as account " +
            " from gbdac " +
            " inner join gbage on gbagecage = gbdaccage " +
            " inner join camca on gbdaccage = camcacage  " +
            " inner join catca on catcatpca = camcatpca  " +
            " inner join gbofi on gbofinofi = gbageagen  " +
            " left join gbcon  gbcon1 on (gbcon1.gbconpfij = 2 and gbcon1.gbconcorr = gbagesexo)  " +
            " left join gbcon gbcon2 on (gbcon2.gbconpfij = 4 and gbcon2.gbconcorr = gbagetdid)  " +
            " left join gbcon gbcon3 on (gbcon3.gbconpfij = 3 and gbcon3.gbconcorr = gbageeciv)  " +
            " left join gbcon gbcon5 on (gbcon5.gbconpfij = 10 and gbcon5.gbconcorr = camcacmon)  " +
            " left join gbprf on gbprfprof = gbageprof  " +
            " left join gbrel on gbrelcagr = gbagecage and gbreltrel = 1 " +
            " inner join gbdoc on gbdoccage = gbagecage  " +
            " inner join gbdir on gbdircage = gbagecage and gbdirtdir = 1 and gbdirmrcb = 0 " +
            " inner join gbprv on gbdircprv = gbprvcprv " +
            " inner join gbciu on gbciuciud = gbdirciud  " +
            " inner join gbpai on gbpaipais = gbdirpais " +
            " inner join gbdpt on gbdptdpto = gbdirdpto  " +
            " inner join gbzon on gbzonzona = gbdirzona " +
            " inner join cadac on cadacncta = camcancta  " +
            " inner join cacon on cadacmoti = caconcorr and caconpref = 30  " +
            " where gbagecage = #{cage}  " +
            " and camcastat = 1 ")
    List<DataFormDto> findDataFormForDigitalBank(@Param("cage") Integer cage);


    @Select(" select gbdaccage as code_client, gbdacnomb as names, gbdacape1 as last_name, " +
            " gbdacape2 as mother_last_name, gbdacape3 as married_last_name, " +
            " trim(gbagendid) as idcard, gbofides1 as office_name, catcadesc as product, " +
            " gbcon1.gbcondesc as gender, gbcon2.gbcondesc as type_document, gbcon3.gbcondesc as civil_status, " +
            " gbdocfvid as expired_date, gbcon5.gbcondesc as currency, gbagefnac as born_date, " +
            " gbdirdire as address_home, gbprfdesc as profession, " +
            " 0.0 as income_mountly, " +
            " gbprvdesc as province, gbdptdesc as departament, gbpaidesc as country, gbzondesc as zone, " +
            " gbdaccelu as cellphone, gbagetlfd as home_phone, " +
            " gbageciiu as activity1, '' as activity2, cacondesc as reason_opening_account, gbrelcage as code_spouse," +
            " camcafapt as opening_date, gbciudesc as city, gbdacmail as email, gbagenomb as full_name_client, " +
            " cafirncta as account " +
            " from gbdac " +
            " inner join gbage on gbagecage = gbdaccage " +
            " inner join cafir on cafircage = gbagecage " +
            " inner join camca on camcancta = cafirncta  " +
            " inner join catca on catcatpca = camcatpca  " +
            " inner join gbofi on gbofinofi = camcaagen  " +
            " left join gbcon  gbcon1 on (gbcon1.gbconpfij = 2 and gbcon1.gbconcorr = gbagesexo)  " +
            " left join gbcon gbcon2 on (gbcon2.gbconpfij = 4 and gbcon2.gbconcorr = gbagetdid)  " +
            " left join gbcon gbcon3 on (gbcon3.gbconpfij = 3 and gbcon3.gbconcorr = gbageeciv)  " +
            " left join gbcon gbcon5 on (gbcon5.gbconpfij = 10 and gbcon5.gbconcorr = camcacmon)  " +
            " left join gbprf on gbprfprof = gbageprof  " +
            " left join gbrel on gbrelcagr = gbagecage and gbreltrel = 1 " +
            " inner join gbdoc on gbdoccage = gbagecage  " +
            " inner join gbdir on gbdircage = gbagecage and gbdirtdir = 1 and gbdirmrcb = 0 " +
            " inner join gbprv on gbdircprv = gbprvcprv " +
            " inner join gbciu on gbciuciud = gbdirciud  " +
            " inner join gbpai on gbpaipais = gbdirpais " +
            " inner join gbdpt on gbdptdpto = gbdirdpto  " +
            " inner join gbzon on gbzonzona = gbdirzona " +
            " inner join cadac on cadacncta = camcancta  " +
            " inner join cacon on cadacmoti = caconcorr and caconpref = 30  " +
            " where gbagecage = #{cage}  " +
            " and cafirncta = #{account}  " +
            " and camcastat = 1 " +
            " and cafirstat = 0 ")
    DataFormDto findDataFormForSavingBankTutor(@Param("cage") Integer cage, @Param("account") String account);


    @Select("select distinct gbagecage as code_client, gbagenomb as full_name_client, gbdirdire as address_home " +
            " ,gbdacnomb as names, gbdacape1 as last_name " +
            " ,gbdacape2 as mother_last_name, gbdacape3 as married_last_name " +
            " , trim(gbagendid) as idcard, gbageciiu as activity1, '' as activity2 " +
            ", gbdaccelu as cellphone, gbagetlfd as home_phone, gbdacmail as email, catitncta as account" +
            " from catit" +
            " inner join camca on catitncta = camcancta and catitcage<>camcacage and camcastat = 1 " +
            " inner join gbage on catitcage = gbagecage " +
            " inner join gbdir on gbdircage = gbagecage and gbdiritem = 1 " +
            " inner join gbdac on gbdaccage = gbagecage " +
            " where catitcage = #{cage}")
    List<DataFormDto> findDataFormSavingBankNoTitular(@Param("cage") Integer cage);
    
    @Select("select distinct gbagecage as code_client, gbagenomb as full_name_client, gbdacnomb as names, gbdacape1 as last_name " +
            "   ,gbdacape2 as mother_last_name, gbdacape3 as married_last_name " +
            "   , gbageciiu as activity1, '' as activity2 " +
            "  , gbdirdire as address_home, trim(gbagendid) as idcard " +
            "  , gbdaccelu as cellphone, gbagetlfd as home_phone, gbdacmail as email, pftitndep as account " +
            " from pftit " +
            "     INNER join pfmdp ON pfmdpndep = pftitndep  AND pfmdpstat = 1 AND pfmdpmane = 2 and pfmdpmrcb = 0 AND pfmdpcage <> pftitcage " +
            "     inner join gbage on pftitcage = gbagecage " +
            "     inner join gbdir on gbdircage = gbagecage and gbdiritem = 1 " +
            "     inner join gbdac on gbdaccage = gbagecage " +
            "     where  " +
            "     pftitcage = #{cage} " +
            "     and pftitmrcb = 0")
    List<DataFormDto> findDataFormDpfNoTitular(@Param("cage") Integer cage);

    @Select("select distinct gbagecage as code_client, gbagenomb as full_name_client, gbdacnomb as names, gbdacape1 as last_name " +
            "     ,gbdacape2 as mother_last_name, gbdacape3 as married_last_name " +
            "     , gbageciiu as activity1, '' as activity2 " +
            "     , gbdirdire as address_home, trim(gbagendid) as idcard " +
            "     , gbdaccelu as cellphone, gbagetlfd as home_phone, gbdacmail as email, pfmdpndep as account " +
            " from pftit " +
            "     INNER join pfmdp ON pfmdpcage = pftitcage  AND pfmdpstat = 1 AND pfmdpmrcb = 0 " +
            "     inner join gbage on pftitcage = gbagecage " +
            "     inner join gbdir on gbdircage = gbagecage and gbdiritem = 1 " +
            "     inner join gbdac on gbdaccage = gbagecage " +
            "     where  " +
            "    pftitmrcb = 0 " +
            "    and pftitcage= #{cage}")
    List<DataFormDto> findDataFormDpfTitular(@Param("cage") Integer cage);

}
