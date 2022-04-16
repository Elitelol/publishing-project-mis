package com.aivarasnakvosas.publishingservicemis.services;

import com.aivarasnakvosas.publishingservicemis.repositories.BudgetCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.BudgetRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.ContractCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.ContractRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskCommentRepository;
import com.aivarasnakvosas.publishingservicemis.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class CommentService {

    @Autowired
    private TaskCommentRepository taskCommentRepository;
    @Autowired
    private BudgetCommentRepository budgetCommentRepository;
    @Autowired
    private ContractCommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private ContractRepository contractRepository;


}
