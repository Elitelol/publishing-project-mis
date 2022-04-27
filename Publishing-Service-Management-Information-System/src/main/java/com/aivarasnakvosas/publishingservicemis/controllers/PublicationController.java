package com.aivarasnakvosas.publishingservicemis.controllers;

import com.aivarasnakvosas.publishingservicemis.dtos.PublicationAcceptanceDTO;
import com.aivarasnakvosas.publishingservicemis.dtos.PublicationDTO;
import com.aivarasnakvosas.publishingservicemis.services.PublicationService;
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

import javax.validation.Valid;
import java.util.List;

/**
 * @author Aivaras Nakvosas
 */
@RestController
@RequestMapping(value = "/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    ResponseEntity<PublicationDTO> savePublication(@Valid @RequestBody PublicationDTO publication) {
        return ResponseEntity.ok(publicationService.savePublication(publication));
    }

    @PostMapping(value = "/{id}/submit")
    ResponseEntity<PublicationDTO> submitPublication(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.submitPublication(id));
    }

    @PostMapping(value = "/changeStatus")
    ResponseEntity<PublicationDTO> changePublicationStatus(@RequestBody PublicationAcceptanceDTO publicationAcceptanceDTO) {
        return ResponseEntity.ok(publicationService.changePublicationStatus(publicationAcceptanceDTO));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<PublicationDTO> getPublication(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    @PostMapping(value = "/assign")
    ResponseEntity<PublicationDTO> addManager(@RequestParam("publicationId") Long publicationId, @RequestParam("managerId") Long managerId) {
        return ResponseEntity.ok(publicationService.addManager(publicationId, managerId));
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<PublicationDTO>> getPublications() {
        return ResponseEntity.ok(publicationService.getPublications());
    }

    @GetMapping(value = "/author")
    ResponseEntity<List<PublicationDTO>> getAuthorPublications(@RequestParam Long id) {
        return ResponseEntity.ok(publicationService.getAuthorPublications(id));
    }

    @GetMapping(value = "/manager")
    ResponseEntity<List<PublicationDTO>> getManagerPublications(@RequestParam Long id) {
        return ResponseEntity.ok(publicationService.getManagerPublications(id));
    }

    @GetMapping(value = "/byProgress")
    ResponseEntity<List<PublicationDTO>> getPublicationsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(publicationService.getPublicationByStatus(status));
    }

    @GetMapping(value = "/unmanaged")
    ResponseEntity<List<PublicationDTO>> getUnmanagedPublications() {
        return ResponseEntity.ok(publicationService.getUnmanagedPublications());
    }

    @PostMapping(value = "/{id}/setComplete")
    ResponseEntity<PublicationDTO> setPublicationComplete(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.setReadyForPublish(id));
    }

    @PostMapping(value = "/{id}/setContract")
    ResponseEntity<PublicationDTO> setContractSigned(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.setContractSigned(id));
    }

    @DeleteMapping
    ResponseEntity<?> deletePublication(@RequestParam Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }
}
