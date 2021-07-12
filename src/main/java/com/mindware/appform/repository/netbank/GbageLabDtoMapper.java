package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GbageLabDtoMapper {

    @Select(" select gbagecage, gbagenomb, gblabdact, gblabitem, gblabitel " +
            " from gbage " +
            " inner join gblab on gbagecage = gblabcage " +
            " where gblabmrcb = 0 " +
            " and gbagecage = #{cage}")
    List<GbageLabDto> findGbageLabByCage(@Param("cage") Integer cage);
}
