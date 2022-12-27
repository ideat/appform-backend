package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Gbofi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface GbofiMapper {

    @Select(" select gbofinofi, gbofidesc, gbofides1" +
            " from gbofi " +
            " where gbofinofi = #{gbofinofi}")
    Optional<Gbofi> findByNofi(@Param("gbofinofi") Integer gbofinofi);
}
