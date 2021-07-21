package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Gblab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GblabMapper {

    @Select("select gblabcage, gblabdact,gblabitel, " +
            " gblabitem, gblabmont, gblabcmon, gblabmpri" +
            " from gblab " +
            " where gblabmrcb = 0 " +
            " and gblabcage = #{cage} " +
            " order by gblabmpri desc")
    List<Gblab> findGblabByCage(@Param("cage") Integer cage);
}
