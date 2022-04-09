package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class ContractDTOMapper {

    public Contract mapToContract(Contract contract, ContractDTO contractDTO){
        contract.setPayment(contractDTO.getPayment());
        contract.setAdvancedPayment(contractDTO.getAdvancedPayment());
        contract.setAppliesAfterPublishing(contractDTO.isAppliesAfterPublishing());
        contract.setOwnedByPublisher(contractDTO.isOwnedByPublisher());
        contract.setAgreements(contractDTO.getAgreements());
        return contract;
    }
}
