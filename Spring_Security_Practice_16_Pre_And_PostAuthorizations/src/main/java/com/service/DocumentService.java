package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.model.Document;
import com.repositorys.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    
    @PreAuthorize("hasPermission(#code, 'document', 'ROLE_admin')")
    public Document preAuthorizeGetDocument(String code) {
        return documentRepository.findDocument(code);
    }

    @PostAuthorize("hasPermission(returnObject, 'ROLE_admin')")
    public Document postAuthorizeGetDocument(String code) {
        return documentRepository.findDocument(code);
    }   
    
}
