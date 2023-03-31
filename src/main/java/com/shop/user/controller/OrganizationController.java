package com.shop.user.controller;

import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.NoRightException;
import com.shop.user.exception.model.OrganizationNameExistsException;
import com.shop.user.model.HttpResponse;
import com.shop.user.service.OrganizationService;
import com.shop.user.utility.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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
import static com.shop.user.controller.security.ValidUsernameSecurity.checkUsernameForValidity;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/user/organization")
@AllArgsConstructor
public class OrganizationController extends ExceptionHandling {

    private final OrganizationService organizationService;
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('user:create_organization')")
    public ResponseEntity<HttpResponse> create(@RequestParam String name,
                                               @RequestParam String description,
                                               @RequestParam String username,
                                               @RequestParam(value = "logoImage", required = false) MultipartFile logoImage) throws IOException, OrganizationNameExistsException {
        organizationService.registerAnOrganization(name, description, username, logoImage);
        return response(OK, APPLICATION_SENT);
    }

    @GetMapping(path = "/image/{username}/{name}", produces = IMAGE_JPEG_VALUE)
    public byte[] getLogoImage(@PathVariable("username") String username,
                               @PathVariable("name") String name,
                               HttpServletRequest request) throws IOException, NoRightException {
        checkUsernameForValidity(request, jwtTokenProvider, username);
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + name));
    }

}
