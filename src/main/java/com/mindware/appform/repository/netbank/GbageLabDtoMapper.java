package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GbageLabDtoMapper {

    @Select(" select gbagecage, gbagendid, gbagenruc, gbagenomb, gblabdact, gblabitem, gblabitel, gbagenaci," +
            " gbagedir1, gbagetlfd, gbdaccelu, gbdacmail, gbageciiu " +
            " from gbage " +
            " inner join gblab on gbagecage = gblabcage " +
            " inner join gbdac on gbdaccage = gbagecage " +
            " where gblabmrcb = 0 " +
            " and gbagecage = #{cage}")
    List<GbageLabDto> findGbageLabDtoByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagendid, gbagenruc, gbagenomb, gblabdact, gblabitem, gblabitel, gbagenaci," +
            " gbagedir1, gbagetlfd,  gbdaccelu, gbdacmail, gbageciiu " +
            " from gbage " +
            " inner join gblab on gbagecage = gblabcage " +
            " inner join gbdac on gbdaccage = gbagecage " +
            " where gblabmrcb = 0 " +
            " and trim(gbagendid) like #{idcard}")
    List<GbageLabDto> findGbageLabDtoByIdCard(@Param("idcard") String idcard);
}
