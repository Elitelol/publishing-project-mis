package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.services.ExportPDFService;
import com.aivarasnakvosas.publishingservicemis.services.PublishingBudgetService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/budget")
public class PublishingBudgetController {

    @Autowired
    private PublishingBudgetService publishingBudgetService;
    @Autowired
    private ExportPDFService exportPDFService;

    @PostMapping
    ResponseEntity<BudgetDTO> savePublishingBudget(@RequestBody BudgetDTO budgetDTO) {
        return ResponseEntity.ok(publishingBudgetService.savePublishingBudget(budgetDTO));
    }

    @PostMapping(value = "/comment")
    ResponseEntity<BudgetDTO> addComment(@Valid @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(publishingBudgetService.addComment(commentDTO));
    }

    @GetMapping(value = "/{publicationId}")
    ResponseEntity<BudgetDTO> getPublishingBudget(@PathVariable Long publicationId) {
        return ResponseEntity.ok(publishingBudgetService.getPublishingBudget(publicationId));
    }

    @GetMapping(value = "/{id}/downloadBudget")
    public ResponseEntity<byte[]> downloadBudgetPDF(@PathVariable Long id) {
        Map<String, Object> data = publishingBudgetService.getBudgetDataForPDF(id);
        String headerString = "attachment; filename =" + id.toString() + "_" + "budget.pdf";
        byte[] bytes = exportPDFService.exportPDF("budget", data);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.setContentLength(bytes.length);
        header.set("Content-Disposition", headerString);
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteComment")
    ResponseEntity<?> deleteBudgetComment(@RequestParam Long id) {
        publishingBudgetService.deleteBudgetComment(id);
        return ResponseEntity.noContent().build();
    }
}
