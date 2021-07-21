package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.AdusrOfi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdusrOfiMapper {

    @Select(" select adusrnomb, adusrusrn, adusragen, gbofides1 " +
            " from adusr " +
            " inner join gbofi on adusragen = gbofinofi " +
            " where adusrusrn = upper(#{login}) ")
    AdusrOfi findByLogin(@Param("login") String login);
}
