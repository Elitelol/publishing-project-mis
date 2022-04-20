package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.services.ContractService;
import com.aivarasnakvosas.publishingservicemis.services.ExportPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private ExportPDFService exportPDFService;

    @PostMapping
    public ResponseEntity<ContractDTO> saveContract(@RequestBody ContractDTO contractDTO) {
        return ResponseEntity.ok(contractService.saveContract(contractDTO));
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<ContractDTO> addComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(contractService.addComment(commentDTO));
    }

    @GetMapping
    public ResponseEntity<ContractDTO> getContract(@RequestParam Long id) {
        return ResponseEntity.ok(contractService.getContract(id));
    }
}
