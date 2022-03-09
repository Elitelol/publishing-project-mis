package com.aivarasnakvosas.publishingservicemis.Controller;

import com.aivarasnakvosas.publishingservicemis.Entity.Publication;
import com.aivarasnakvosas.publishingservicemis.Service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/{id}")
    ResponseEntity<Publication> getPublication(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    ResponseEntity<Publication> savePublication(Publication publication) {
        return ResponseEntity.ok(publicationService.savePublication(publication));
    }
}
