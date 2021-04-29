package com.controllers;

import com.model.Document;
import com.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/documents/pre/{code}")
    public Document preGetDetails(@PathVariable String code) {
        return documentService.preAuthorizeGetDocument(code);
    }
    
    @GetMapping("/documents/post/{code}")
    public Document postGetDetails(@PathVariable String code) {
        return documentService.postAuthorizeGetDocument(code);
    }
}
