package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.entity.Contract;
import com.aivarasnakvosas.publishingservicemis.entity.ContractComment;
import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.User;
import com.aivarasnakvosas.publishingservicemis.exceptions.EntityNotFoundException;
import com.aivarasnakvosas.publishingservicemis.mappers.CommentDTOMapper;
import com.aivarasnakvosas.publishingservicemis.mappers.ContractDTOMapper;
import com.aivarasnakvosas.publishingservicemis.repositories.ContractCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Aivaras Nakvosas
 */
@Service
@Transactional
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractCommentRepository contractCommentRepository;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContractDTOMapper contractDTOMapper;
    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public ContractDTO saveContract(ContractDTO contractDTO) {
        Publication publication = publicationService.findPublication(contractDTO.getPublicationId());
        Contract contract = contractDTO.getContractId() != null ? findContract(contractDTO.getContractId()) : new Contract();
        contractDTOMapper.mapToContract(contract, contractDTO, publication);
        contractRepository.save(contract);
        return contractDTOMapper.mapToDTO(contract);
    }

    public ContractDTO addComment(CommentDTO commentDTO) {
        Contract contract = findContract(commentDTO.getEntityId());
        User user = userService.getUser(commentDTO.getUserId());
        ContractComment rootComment = null;
        if (commentDTO.getRootCommentId() != null) {
            rootComment = contractCommentRepository.getById(commentDTO.getRootCommentId());
        }
        ContractComment contractComment = commentDTOMapper.mapToContractComment(commentDTO, contract, user, rootComment);
        contract.addComment(contractComment);
        contractRepository.save(contract);
        return contractDTOMapper.mapToDTO(contract);
    }

    public ContractDTO getContract(Long id) {
        Contract contract = findContract(id);
        return contractDTOMapper.mapToDTO(contract);
    }

    private Contract findContract(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isEmpty()) {
            throw new EntityNotFoundException(Contract.class);
        }
        return contract.get();
    }

    public Map<String, Object> getContractDataForPDF(Long id) {
        Map<String, Object> contractData = new HashMap<>();
        return contractData;
    }
}
