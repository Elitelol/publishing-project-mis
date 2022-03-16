package com.aivarasnakvosas.publishingservicemis.entity;

import org.apache.catalina.User;

/**
 * @author Aivaras Nakvosas
 */
//@Entity(name = "Comment")
public class Comment extends AbstractBasicEntity {

    private User commentator;

    private String text;

    private Comment rootComment;

    private Comment reply;

}
