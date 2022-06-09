package com.example.demo.utility;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class HtmlToPdfConverter {
    public Integer a = 1000;
    public  Document generatePDFFromHTML(String filename) {
        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document,
                    new FileOutputStream("resume/html.pdf"));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new FileInputStream(filename));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        document.close();
        return document;
    }
    public byte[] generatePdfFromHtml(String html, String name) throws IOException  {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, buffer);
        byte[] pdfAsBytes = buffer.toByteArray();
        try (FileOutputStream fos = new FileOutputStream(name)) {
            fos.write(pdfAsBytes);
        }
        return pdfAsBytes;
    }
}
