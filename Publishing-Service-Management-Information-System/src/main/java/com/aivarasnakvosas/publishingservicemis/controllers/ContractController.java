package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.services.ContractService;
import com.aivarasnakvosas.publishingservicemis.services.ExportPDFService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ContractDTO> addComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(contractService.addComment(commentDTO));
    }

    @GetMapping
    public ResponseEntity<ContractDTO> getContract(@RequestParam Long id) {
        return ResponseEntity.ok(contractService.getContract(id));
    }

    @GetMapping(value = "/{id}/downloadContract")
    public void downloadBudgetPDF(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        Map<String, Object> data = contractService.getContractDataForPDF(id);
        String header = "attachment; filename =" + id.toString() + "_" + "contract.pdf";
        ByteArrayInputStream byteArrayInputStream = exportPDFService.exportPDF("contract", data);
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-Disposition", header);
        IOUtils.copy(byteArrayInputStream, httpServletResponse.getOutputStream());
    }

    @DeleteMapping(value = "/deleteComment")
    public ResponseEntity<?> deleteContractComment(Long id) {
        contractService.deleteContractComment(id);
        return ResponseEntity.noContent().build();
    }
}
