package com.shop.user.controller;

import com.shop.user.model.HttpResponse;
import com.shop.user.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.shop.user.constant.FileConstant.*;
import static com.shop.user.constant.HttpAnswer.APPLICATION_SENT;
import static com.shop.user.constant.HttpAnswer.response;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/user/organization")
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('user:create_organization')")
    public ResponseEntity<HttpResponse> create(@RequestParam String name,
                                               @RequestParam String description,
                                               @RequestParam String username,
                                               @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {
        organizationService.registerAnOrganization(name, description, username, profileImage);
        return response(OK, APPLICATION_SENT);
    }

    @GetMapping(path = "/image/{username}/{name}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username,
                                  @PathVariable("name") String name) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + name));
    }

}
