package com.aivarasnakvosas.publishingservicemis.services;

import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class ExportPDFService {

    private Logger logger = LoggerFactory.getLogger(ExportPDFService.class);

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] exportPDF(String templateName, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ITextRenderer iTextRenderer = new ITextRenderer();
            iTextRenderer.setDocumentFromString(htmlContent);
            iTextRenderer.layout();
            iTextRenderer.createPDF(byteArrayOutputStream, false);
            iTextRenderer.finishPDF();
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }
}
