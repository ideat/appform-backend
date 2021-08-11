package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Gbcon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GbconMapper {

    @Select(" select gbconpfij, gbconcorr, gbcondesc, gbconabre " +
            " from gbcon order by 1,2")
    List<Gbcon> findAll();
}
