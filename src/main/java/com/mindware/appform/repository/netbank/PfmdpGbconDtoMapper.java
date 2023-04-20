package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.PfmdpGbconDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PfmdpGbconDtoMapper {

    @Select(" select pfmdpndep, gbcondesc as pfmdpcmon, pfmdpplzo, pfmdpcapi " +
            " from informix.pfmdp " +
            " inner join gbcon on gbconpfij =10 and gbconcorr = pfmdpcmon" +
            " where  pfmdpndep = #{pfmdpndep}")
    PfmdpGbconDto getPfmdpGbcon(@Param("pfmdpndep") String pfmdpndep);
}
