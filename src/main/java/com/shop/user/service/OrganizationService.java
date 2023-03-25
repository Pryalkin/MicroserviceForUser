package com.shop.user.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OrganizationService {
    void registerAnOrganization(String name, String description, String username, MultipartFile profileImage) throws IOException;
}
