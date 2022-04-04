package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.entity.dtos.PublicationDTO;
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

    @RequestMapping(value = "/{id}/{managerId}")
    ResponseEntity<Publication> addManager(@PathVariable("id") Long publicationId, @PathVariable("managerId") Long managerId) {
        return ResponseEntity.ok(publicationService.addManager(publicationId, managerId));
    }
}
