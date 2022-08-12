package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.GbdanGbageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface GbdanGbageDtoMapper {

    @Select(" select gbdancage, trim(gbdannomc) gbdannomc,  trim(gbdandact) gbdandact, " +
            "trim(gbdannidc) gbdannidc, gbdanprof, gbprfdesc " +
            "from gbdan " +
            "inner join gbage on gbagecage = gbdancage " +
            "inner join gbdac on gbdaccage = gbdancage " +
            "left join gbprf on gbdanprof = gbprfprof " +
            "where gbdancage = #{cage} " +
            "and gbdaccony is null")
    Optional<GbdanGbageDto> findGbdanGbageDtoByCage(@Param("cage") Integer cage);
}
