package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.ContractDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Publication> savePublication(@RequestBody PublicationDTO publication) {
        return ResponseEntity.ok(publicationService.savePublication(publication));
    }

    @PostMapping(value = "/changeStatus")
    ResponseEntity<Publication> changePublicationStatus(@RequestBody PublicationAcceptanceDTO publicationAcceptanceDTO) {
        return ResponseEntity.ok(publicationService.changePublicationStatus(publicationAcceptanceDTO));
    }

    @GetMapping
    ResponseEntity<Publication> getPublication(@RequestParam Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    @PostMapping(value = "/{id}/{managerId}")
    ResponseEntity<Publication> addManager(@PathVariable("id") Long publicationId, @PathVariable("managerId") Long managerId) {
        return ResponseEntity.ok(publicationService.addManager(publicationId, managerId));
    }

    @PostMapping(value = "/contract")
    ResponseEntity<Publication> saveContract(@RequestBody ContractDTO contractDTO) {
        return ResponseEntity.ok(publicationService.saveContract(contractDTO));
    }

    @PostMapping(value = "/budget")
    ResponseEntity<Publication> saveBudget(@RequestBody BudgetDTO budgetDTO) {
        return ResponseEntity.ok(publicationService.saveBudget(budgetDTO));
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<Publication>> getPublications() {
        return ResponseEntity.ok(publicationService.getPublications());
    }

    @GetMapping(value = "/author")
    ResponseEntity<List<Publication>> getAuthorPublications(@RequestParam Long id) {
        return ResponseEntity.ok(publicationService.getAuthorPublications(id));
    }

    @GetMapping(value = "/manager")
    ResponseEntity<List<Publication>> getManagerPublications(@RequestParam Long id) {
        return ResponseEntity.ok(publicationService.getManagerPublications(id));
    }

    @GetMapping(value = "/byProgress")
    ResponseEntity<List<Publication>> getPublicationsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(publicationService.getPublicationByStatus(status));
    }
}
