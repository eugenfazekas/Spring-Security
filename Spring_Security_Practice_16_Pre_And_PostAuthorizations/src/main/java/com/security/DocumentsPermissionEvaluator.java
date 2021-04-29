package com.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.model.Document;
import com.repositorys.DocumentRepository;

@Component
public class DocumentsPermissionEvaluator implements PermissionEvaluator {
	
	@Autowired
    private DocumentRepository documentRepository;


    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object target,
                                 Object permission) {
        Document document = (Document) target;
        String p = (String) permission;

        boolean admin =
           authentication.getAuthorities()
           .stream()
           .anyMatch(a -> a.getAuthority().equals(p));

        return admin || document.getOwner().equals(authentication.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        String code = targetId.toString();
        Document document = documentRepository.findDocument(code);

        String p = (String) permission;


        boolean admin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals(p));

        return admin || document.getOwner().equals(authentication.getName());
    
    }
}
