package com.esfe.Asistencia.Utlidades;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class PdfGeneratorService {
    @Autowired
    private SpringTemplateEngine templateEngine;

    public byte[] generatePdfReport (String templateName, Map<String,Object> data ) throws Exception{

        //Preparar contexto de thymeleaf
        Context context = new Context();
        context.setVariables(data);

        //Procesar la plantilla html
        String htlmContent = templateEngine.process(templateName, context);


        try(ByteArrayOutputStream  outputStream = new ByteArrayOutputStream()){
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();
            builder.withHtmlContent(htlmContent, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        }

    }

}
