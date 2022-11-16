package com.mindware.appform.service;

import com.mindware.appform.entity.Contract;
import com.mindware.appform.repository.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContractService {

    @Autowired
    ContractMapper mapper;

    public Contract create(Contract contract){
        contract.setId(UUID.randomUUID().toString());
        mapper.add(contract);
        return contract;
    }

    public Contract update(Contract contract){
        mapper.update(contract);
        return contract;
    }

    public Optional<Contract> findById(String id){
        return mapper.findById(id);
    }

    public Optional<Contract> findByAccount(String account){
        return mapper.findByAccount(account);
    }
}
