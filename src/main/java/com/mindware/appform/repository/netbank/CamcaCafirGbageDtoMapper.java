package com.mindware.appform.repository.netbank;

import com.mindware.appform.dto.DataContractSavingBankDto;
import com.mindware.appform.entity.netbank.dto.CamcaCafirGbageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CamcaCafirGbageDtoMapper {

    @Select(" select camcancta, gbagenomb, gbagendid, gbcondesc, gbconabre " +
            " from camca " +
            " inner join cafir on cafirncta = camcancta " +
            " inner join gbage on gbagecage = cafircage " +
            " inner join gbcon on gbconpfij = 10 and gbconcorr = camcacmon " +
            " where camcancta = #{account} " +
            " and cafirstat = 0 " +
            " and camcastat in (1,2,3)")
    List<CamcaCafirGbageDto> getDataContractSavingBank(@Param("account") String account);


}
