package com.example.xml.Controller;

import com.example.xml.Service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/xml")
public class XmlController {

    @Autowired
    private XmlService xmlService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadXml(@RequestParam("file") MultipartFile file, @RequestParam("invoiceID") String invoiceID) {
        String result = xmlService.processXmlFile(file, invoiceID);
        return ResponseEntity.ok(result);
    }



}
