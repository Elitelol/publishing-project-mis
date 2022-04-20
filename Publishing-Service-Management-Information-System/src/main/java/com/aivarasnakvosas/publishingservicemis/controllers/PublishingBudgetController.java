package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.services.ExportPDFService;
import com.aivarasnakvosas.publishingservicemis.services.PublishingBudgetService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<BudgetDTO> addComment(@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(publishingBudgetService.addComment(commentDTO));
    }

    @GetMapping
    ResponseEntity<BudgetDTO> getPublishingBudget(@RequestParam Long id) {
        return ResponseEntity.ok(publishingBudgetService.getPublishingBudget(id));
    }

    @GetMapping(value = "/{id}/downloadBudget")
    public void downloadBudgetPDF(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        BudgetDTO budget = publishingBudgetService.getPublishingBudget(id);
        Map<String, Object> data = new HashMap<>();
        data.put("budget", budget);
        ByteArrayInputStream byteArrayInputStream = exportPDFService.exportPDF("budget", data);
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
        IOUtils.copy(byteArrayInputStream, httpServletResponse.getOutputStream());
    }
}