package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.services.ContractService;
import com.aivarasnakvosas.publishingservicemis.services.ExportPDFService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

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
    public ResponseEntity<ContractDTO> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(contractService.addComment(commentDTO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContract(id));
    }

    @GetMapping(value = "/{id}/downloadContract")
    public ResponseEntity<byte[]> downloadBudgetPDF(@PathVariable Long id) throws IOException {
        Map<String, Object> data = contractService.getContractDataForPDF(id);
        String headerString = "attachment; filename =" + id.toString() + "_" + "contract.pdf";
        byte[] bytes = exportPDFService.exportPDF("contract", data);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.setContentLength(bytes.length);
        header.set("Content-Disposition", headerString);
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteComment")
    public ResponseEntity<?> deleteContractComment(Long id) {
        contractService.deleteContractComment(id);
        return ResponseEntity.noContent().build();
    }
}
