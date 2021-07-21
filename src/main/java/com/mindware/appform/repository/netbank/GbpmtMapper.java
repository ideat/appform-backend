package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Gbpmt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GbpmtMapper {

    @Select(" select gbpmtfdia, gbpmttcof, gbpmtnemp " +
            " from gbpmt")
    Gbpmt findAll();
}
