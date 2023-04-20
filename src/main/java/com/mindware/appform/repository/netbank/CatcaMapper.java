package com.mindware.appform.repository.netbank;

import com.mindware.appform.entity.netbank.Catca;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CatcaMapper {

    @Select(" select catcatpca, catcadesc from catca order by 1")
    List<Catca> findAll();
}
