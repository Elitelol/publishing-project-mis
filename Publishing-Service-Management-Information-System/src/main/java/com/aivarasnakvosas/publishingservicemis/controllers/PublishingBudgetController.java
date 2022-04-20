package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.CommentDTO;
import com.aivarasnakvosas.publishingservicemis.services.PublishingBudgetService;
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
@RequestMapping(value = "/budget")
public class PublishingBudgetController {

    @Autowired
    private PublishingBudgetService publishingBudgetService;

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
}
