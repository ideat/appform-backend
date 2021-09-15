package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.dto.CamcaCatcaDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CamcaCatcaDtoMapper {

    @Select(" select camcancta as account, catcadesc as productName  " +
            " from camca " +
            " inner join catca on catcatpca = camcatpca " +
            " where camcancta = #{account}")
    CamcaCatcaDto findByAccount(@Param("account") String account);
}
