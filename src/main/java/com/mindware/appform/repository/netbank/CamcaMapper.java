package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Camca;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CamcaMapper {

    @Select("select camcacage, camcancta, camcacmon, camcasact, camcafapt, camcastus " +
            " from camca " +
            " where camcancta = #{ncta} " +
            " and camcastat in (1,2,3) " +
            " and camcamane = 3")
    Camca findCamcaByNcta(@Param("ncta") String ncta);

}
