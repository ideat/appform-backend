package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Cacon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CaconMapper {

    @Select("select * from cacor order by caconpref,caconcorr")
    List<Cacon> getAll();

    @Select("Select caconpref, caconcorr, trim(cacondesc) as cacondesc from cacon " +
            " where caconpref = #{caconpref}  " +
            " and caconcorr > 0 " +
            " order by caconcorr")
    List<Cacon> getByPref(@Param("caconpref") Integer caconpref);
}
