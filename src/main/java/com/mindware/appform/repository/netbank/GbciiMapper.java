package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Gbcii;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface GbciiMapper {
    @Select(" select gbciiciiu, gbciidesc from gbcii")
    List<Gbcii> getAll();

    @Select("select gbciiciiu, gbciidesc from gbcii where gbciiciiu = #{gbciiciiu}")
    Optional<Gbcii> findByGbciiu(Integer gbciiciiu);
}
