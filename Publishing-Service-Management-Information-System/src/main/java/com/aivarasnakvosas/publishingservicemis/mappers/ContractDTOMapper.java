package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class ContractDTOMapper {

    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public void mapToContract(Contract contract, ContractDTO contractDTO, Publication publication) {
        contract.setPayment(contractDTO.getPayment());
        contract.setAdvancedPayment(contractDTO.getAdvancedPayment());
        contract.setAppliesAfterPublishing(contractDTO.isAppliesAfterPublishing());
        contract.setOwnedByPublisher(contractDTO.isOwnedByPublisher());
        contract.setAgreements(contractDTO.getAgreements());
        contract.setPublication(publication);
    }

    public ContractDTO mapToDTO(Contract contract) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setPublicationId(contract.getPublication().getId());
        contractDTO.setContractId(contract.getId());
        contractDTO.setPayment(contract.getPayment());
        contractDTO.setAdvancedPayment(contract.getAdvancedPayment());
        contractDTO.setAppliesAfterPublishing(contract.isAppliesAfterPublishing());
        contractDTO.setOwnedByPublisher(contract.isOwnedByPublisher());
        contractDTO.setAgreements(contract.getAgreements());
        contractDTO.setComments(contract.getComments().stream()
                .filter(comment -> comment.getRootComment() == null)
                .map(contractComment -> commentDTOMapper.mapToDTO(contractComment))
                .collect(Collectors.toList()));
        return contractDTO;
    }
}
