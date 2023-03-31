package com.shop.user.service.impl;

import com.shop.user.exception.model.OrganizationNameExistsException;
import com.shop.user.model.user.Organization;
import com.shop.user.model.user.User;
import com.shop.user.repository.OrganizationRepo;
import com.shop.user.repository.UserRepo;
import com.shop.user.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.shop.user.constant.ExceptionConstant.ORGANIZATION_NAME_EXISTS;
import static com.shop.user.constant.FileConstant.*;
import static com.shop.user.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;
import static com.shop.user.enumeration.Activity.UNREGISTERED;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final UserRepo userRepo;
    private final OrganizationRepo organizationRepo;

    @Override
    @Transactional
    public void registerAnOrganization(String name, String description, String username, MultipartFile logoImage) throws IOException, OrganizationNameExistsException {
        validateNameOrganization(name);
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username));
        Organization organization = new Organization();
        organization.setName(name);
        organization.setDescription(description);
        organization.setActivity(UNREGISTERED.name());
        organization = saveLogoImage(organization, user.getUsername(), logoImage);
        user.addOrganization(organization);
        userRepo.save(user);
    }

    private void validateNameOrganization(String name) throws OrganizationNameExistsException {
        if (organizationRepo.findByName(name).isPresent()){
            throw new OrganizationNameExistsException(ORGANIZATION_NAME_EXISTS);
        }
    }

    private Organization saveLogoImage(Organization organization, String username, MultipartFile logoImage) throws IOException {
        if (logoImage != null){
            Path userFolder = Paths.get(USER_FOLDER + username).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + organization.getName() + DOT + JPG_EXTENSION));
            Files.copy(logoImage.getInputStream(), userFolder.resolve(organization.getName() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            organization.setLogo(setLogoImageUrl(username, organization.getName()));
            return organization;
        }
        return organization;
    }

    private String setLogoImageUrl(String username, String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().
                path(USER_IMAGE_PATH + username + FORWARD_SLASH + name + DOT + JPG_EXTENSION).toUriString();
    }
}
