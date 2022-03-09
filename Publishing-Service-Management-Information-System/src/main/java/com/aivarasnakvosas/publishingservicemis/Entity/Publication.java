package com.aivarasnakvosas.publishingservicemis.Entity;

import lombok.Data;

/**
 * @author Aivaras Nakvosas
 */
@Data
public class Publication {

    private Long id;
    private String name;
    private User author;
    private PublicationType publicationType;

}
