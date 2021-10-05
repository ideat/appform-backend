package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.PfmdpGbageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PfmdpGbageDtoMapper {

    @Select(" select pfmdpndep, gbagenomb, gbagendid,gbcondesc, gbconabre, pfmdpcage, " +
            " pfmdpcag2, pfmdpcag3, pfmdpcag4 " +
            " from pfmdp " +
            " inner join gbage on pfmdpcage = gbagecage " +
            " inner join gbcon on gbconpfij = 10 and gbconcorr = pfmdpcmon " +
            " and pfmdpstat in (1,4) " +
            " where pfmdpndep = #{account}")
    PfmdpGbageDto getDataContractDpf(@Param("account") Double account);


    @Select(" select pfmdpndep, gbagenomb, gbagendid,gbcondesc, gbconabre, pfmdpcage, " +
            "  pftitcage as pfmdpcag2 " +
            " from pfmdp " +
            " left join pftit on pftitndep = pfmdpndep and pftitmrcb = 0" +
            " inner join gbage on pfmdpcage = gbagecage " +
            " inner join gbcon on gbconpfij = 10 and gbconcorr = pfmdpcmon " +
            " and pfmdpstat in (1,4) " +
            " where pfmdpndep = #{account}")
    List<PfmdpGbageDto> getListDataContractDpf(@Param("account") Double account);

}
