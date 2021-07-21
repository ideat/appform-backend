package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Gbcae;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GbcaeMapper {
    @Select(" select * from gbcae")
    List<Gbcae> getAll();

    @Select("select *from gbcae where gbcaeciiu = #{gbcaeciiu}")
    Optional<Gbcae> findByCiiu(@Param("gbcaeciiu") Integer gbcaeciiu);
}
