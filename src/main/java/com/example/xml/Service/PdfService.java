package com.example.xml.Service;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PdfService {

    public byte[] convertXmlToPdf(InputStream xmlInputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDoc = builder.parse(xmlInputStream);

            Element root = xmlDoc.getDocumentElement();
            NodeList children = root.getChildNodes();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.itextpdf.text.Document pdfDoc = new com.itextpdf.text.Document();
            PdfWriter.getInstance(pdfDoc, baos);
            pdfDoc.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            for (int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String tag = node.getNodeName();
                    String value = node.getTextContent();

                    table.addCell(tag);
                    table.addCell(value);
                }
            }

            pdfDoc.add(table);
            pdfDoc.close();

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}