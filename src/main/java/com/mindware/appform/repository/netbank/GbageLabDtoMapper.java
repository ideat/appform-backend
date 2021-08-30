package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GbageLabDtoMapper {

    @Select(" select gbagecage, gbagendid, gbagenruc, gbagenomb, gblabdact, gblabitem, gblabitel, gbagenaci," +
            " gbagedir1, gbagetlfd, gbdaccelu, gbdacmail, gbageciiu, trim(gbcaedesc) gbcaedesc " +
            " from gbage " +
            " inner join gbdac on gbdaccage = gbagecage " +
            " left join gblab on gbagecage = gblabcage and  gblabmrcb = 0 " +
            " left join gbcae on gbcaeciiu = gbageciiu " +
            " where gbagecage = #{cage}")
    List<GbageLabDto> findGbageLabDtoByCage(@Param("cage") Integer cage);

    @Select(" select gbagecage, gbagendid, gbagenruc, gbagenomb, gblabdact, gblabitem, gblabitel, gbagenaci," +
            " gbagedir1, gbagetlfd,  gbdaccelu, gbdacmail, gbageciiu, trim(gbcaedesc) gbcaedesc " +
            " from gbage " +
            " inner join gbdac on gbdaccage = gbagecage " +
            " left join gblab on gbagecage = gblabcage and  gblabmrcb = 0 " +
            " left join gbcae on gbcaeciiu = gbageciiu" +
            " where trim(gbagendid) like #{idcard}")
    List<GbageLabDto> findGbageLabDtoByIdCard(@Param("idcard") String idcard);
}
