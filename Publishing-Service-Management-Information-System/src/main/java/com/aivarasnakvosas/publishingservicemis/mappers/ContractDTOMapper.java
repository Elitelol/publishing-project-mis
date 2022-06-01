package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.entity.ContractComment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class ContractDTOMapper {

    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public void mapToContract(Contract contract, ContractDTO contractDTO, Publication publication) {
        contract.setPublishDate(contractDTO.getPublishDate());
        contract.setPublicationPrice(contractDTO.getPublicationPrice());
        publication.setPublishDate(contractDTO.getPublishDate());
        publication.setPrice(contractDTO.getPublicationPrice());
        contract.setAmountOnSigningContract(contractDTO.getAmountOnSigningContract());
        contract.setAmountOfCompletedManuscript(contractDTO.getAmountOfCompletedManuscript());
        contract.setAmountOnInitialPublish(contractDTO.getAmountOnInitialPublish());
        contract.setWithinMonthsAfterPublish(contractDTO.getWithinMonthsAfterPublish());
        contract.setFirstCoverRate(contractDTO.getFirstCoverRate());
        contract.setFirstCoverPercent(contractDTO.getFirstCoverPercent());
        contract.setSecondCoverRate(contractDTO.getSecondCoverRate());
        contract.setSecondCoverPercent(contractDTO.getSecondCoverPercent());
        contract.setLastCoverRate(contractDTO.getLastCoverRate());
        contract.setLastCoverPercent(contractDTO.getLastCoverPercent());
        contract.setPublication(publication);
    }

    public ContractDTO mapToDTO(Contract contract) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractId(contract.getId());
        contractDTO.setPublicationId(contract.getPublication().getId());
        contractDTO.setPublishDate(contract.getPublishDate());
        contractDTO.setPublicationPrice(contract.getPublicationPrice());
        contractDTO.setAmountOnSigningContract(contract.getAmountOnSigningContract());
        contractDTO.setAmountOfCompletedManuscript(contract.getAmountOfCompletedManuscript());
        contractDTO.setAmountOnInitialPublish(contract.getAmountOnInitialPublish());
        contractDTO.setWithinMonthsAfterPublish(contract.getWithinMonthsAfterPublish());
        contractDTO.setFirstCoverRate(contract.getFirstCoverRate());
        contractDTO.setFirstCoverPercent(contract.getFirstCoverPercent());
        contractDTO.setSecondCoverRate(contract.getSecondCoverRate());
        contractDTO.setSecondCoverPercent(contract.getSecondCoverPercent());
        contractDTO.setLastCoverRate(contract.getLastCoverRate());
        contractDTO.setLastCoverPercent(contract.getLastCoverPercent());
        List<ContractComment> distinctComments = contract.getComments()
                .stream()
                .distinct()
                .collect(Collectors.toList());
        contractDTO.setComments(distinctComments.stream()
                .filter(comment -> comment.getRootComment() == null)
                .map(contractComment -> commentDTOMapper.mapToDTO(contractComment))
                .collect(Collectors.toList()));
        return contractDTO;
    }
}
