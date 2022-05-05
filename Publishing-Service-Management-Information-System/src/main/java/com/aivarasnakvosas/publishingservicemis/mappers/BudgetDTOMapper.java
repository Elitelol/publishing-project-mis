package com.aivarasnakvosas.publishingservicemis.mappers;

import com.aivarasnakvosas.publishingservicemis.entity.Publication;
import com.aivarasnakvosas.publishingservicemis.entity.PublishingBudget;
import com.aivarasnakvosas.publishingservicemis.dtos.BudgetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class BudgetDTOMapper {

    @Autowired
    private CommentDTOMapper commentDTOMapper;

    public void mapToPublishingBudget(PublishingBudget publishingBudget, BudgetDTO budgetDTO, Publication publication){
        publishingBudget.setPageNumber(budgetDTO.getPageNumber());
        publication.setPageNumber(budgetDTO.getPageNumber());
        publishingBudget.setNumberOfCopies(budgetDTO.getNumberOfCopies());
        publishingBudget.setCopyEditingRate(budgetDTO.getCopyEditingRate());
        publishingBudget.setProofReadingRate(budgetDTO.getProofReadingRate());
        publishingBudget.setPurchaseOfPhotosRate(budgetDTO.getPurchaseOfPhotosRate());
        publishingBudget.setPurchaseOfPhotosQuantity(budgetDTO.getPurchaseOfPhotosQuantity());
        publishingBudget.setCoverDesignRate(budgetDTO.getCoverDesignRate());
        publishingBudget.setCoverDesignQuantity(budgetDTO.getCoverDesignQuantity());
        publishingBudget.setInteriorLayoutRate(budgetDTO.getInteriorLayoutRate());
        publishingBudget.setPrintingRate(budgetDTO.getPrintingRate());
        publishingBudget.setColourPrintingRate(budgetDTO.getColourPrintingRate());
        publishingBudget.setDeliveryToStorageRate(budgetDTO.getDeliveryToStorageRate());
        publishingBudget.setAdvertisingCost(budgetDTO.getAdvertisingCost());
        publishingBudget.setCopyMailingCost(budgetDTO.getCopyMailingCost());
        publishingBudget.setPublication(publication);
    }

    public BudgetDTO mapToDTO(PublishingBudget publishingBudget) {
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setPublicationId(publishingBudget.getPublication().getId());
        budgetDTO.setBudgetId(publishingBudget.getId());
        budgetDTO.setNumberOfCopies(publishingBudget.getNumberOfCopies());
        budgetDTO.setCopyEditingRate(publishingBudget.getCopyEditingRate());
        budgetDTO.setProofReadingRate(publishingBudget.getProofReadingRate());
        budgetDTO.setPurchaseOfPhotosRate(publishingBudget.getPurchaseOfPhotosRate());
        budgetDTO.setPurchaseOfPhotosQuantity(publishingBudget.getPurchaseOfPhotosQuantity());
        budgetDTO.setCoverDesignRate(publishingBudget.getCoverDesignRate());
        budgetDTO.setCoverDesignQuantity(publishingBudget.getCoverDesignQuantity());
        budgetDTO.setInteriorLayoutRate(publishingBudget.getInteriorLayoutRate());
        budgetDTO.setPrintingRate(publishingBudget.getPrintingRate());
        budgetDTO.setColourPrintingRate(publishingBudget.getColourPrintingRate());
        budgetDTO.setDeliveryToStorageRate(publishingBudget.getDeliveryToStorageRate());
        budgetDTO.setAdvertisingCost(publishingBudget.getAdvertisingCost());
        budgetDTO.setCopyMailingCost(publishingBudget.getCopyMailingCost());
        budgetDTO.setComments(publishingBudget.getComments().stream()
                .filter(comment -> comment.getRootComment() == null)
                .map(budgetComment -> commentDTOMapper.mapToDTO(budgetComment))
                .collect(Collectors.toList()));
        return budgetDTO;
    }
}
