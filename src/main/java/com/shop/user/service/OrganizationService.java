package com.shop.user.service;

import com.shop.user.exception.model.OrganizationNameExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OrganizationService {
    void registerAnOrganization(String name, String description, String username, MultipartFile profileImage) throws IOException, OrganizationNameExistsException;
}
