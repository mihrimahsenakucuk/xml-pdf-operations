package com.example.xml.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class XmlService {
    private final String targetFolder = "C:/xmlyenidosyasi";

    public String processXmlFile(MultipartFile file, String targetInvoiceID) {
        try {
            File tempFile = File.createTempFile("upload", ".xml");
            file.transferTo(tempFile);

            if (isXmlFile(tempFile)) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(tempFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("InvoiceDetailRequestHeader");
                if (nList.getLength() > 0) {
                    Element element = (Element) nList.item(0);
                    String invoiceID = element.getAttribute("invoiceID");
                    if (invoiceID.equals(targetInvoiceID)) {
                        new File(targetFolder).mkdirs();
                        Path targetPath = Paths.get(targetFolder, file.getOriginalFilename());
                        Files.copy(tempFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                        return "Eşleşen dosya kopyalandı: " + file.getOriginalFilename();
                    }
                }
            }
            return "Eşleşme bulunamadı: " + file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            return "İşlem sırasında hata oluştu: " + e.getMessage();
        }
    }

    private boolean isXmlFile(File file) {
        try {
            String fileName = file.getName().toLowerCase();
            if (fileName.endsWith(".zip") || fileName.endsWith(".exe") || fileName.endsWith(".jpg")) {
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.parse(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}