package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.ImageMapper;
import com.beautysalon.domain.entities.UserEntity;
import com.beautysalon.domain.entities.UserRole;
import com.beautysalon.domain.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/panel")
public class UserMvcController {

    private static class UserModel {
        private Long id;
        private String username;
        private String password;
        private boolean accessToAdminPanel;
        private boolean serviceEditor;
        private boolean imageEditor;
        private boolean employeeEditor;
        private boolean appointmentEditor;
        private boolean accountEditor;
        private String imageUrl;
        private List<ImageDto> images;

        public UserModel() {
        }

        public UserModel(Long id, String username, String password, boolean accessToAdminPanel, boolean serviceEditor, boolean imageEditor, boolean employeeEditor, boolean appointmentEditor, boolean accountEditor,String imageUrl, List<ImageDto> images) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.accessToAdminPanel = accessToAdminPanel;
            this.serviceEditor = serviceEditor;
            this.imageEditor = imageEditor;
            this.employeeEditor = employeeEditor;
            this.appointmentEditor = appointmentEditor;
            this.accountEditor = accountEditor;
            this.imageUrl = imageUrl;
            this.images = images;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isAccessToAdminPanel() {
            return accessToAdminPanel;
        }

        public void setAccessToAdminPanel(boolean accessToAdminPanel) {
            this.accessToAdminPanel = accessToAdminPanel;
        }

        public boolean isServiceEditor() {
            return serviceEditor;
        }

        public void setServiceEditor(boolean serviceEditor) {
            this.serviceEditor = serviceEditor;
        }

        public boolean isImageEditor() {
            return imageEditor;
        }

        public void setImageEditor(boolean imageEditor) {
            this.imageEditor = imageEditor;
        }

        public boolean isEmployeeEditor() {
            return employeeEditor;
        }

        public void setEmployeeEditor(boolean employeeEditor) {
            this.employeeEditor = employeeEditor;
        }

        public boolean isAppointmentEditor() {
            return appointmentEditor;
        }

        public void setAppointmentEditor(boolean appointmentEditor) {
            this.appointmentEditor = appointmentEditor;
        }

        public boolean isAccountEditor() {
            return accountEditor;
        }

        public void setAccountEditor(boolean accountEditor) {
            this.accountEditor = accountEditor;
        }

        public List<ImageDto> getImages() {
            return images;
        }

        public void setImages(List<ImageDto> images) {
            this.images = images;
        }
    }

    private final UserService userService;
    private final ImageMapper imageMapper;

    public UserMvcController(UserService userService, ImageMapper imageMapper) {
        this.userService = userService;
        this.imageMapper = imageMapper;
    }

    @GetMapping("/users")
    public String usersGet(
            Model model,
            HttpServletRequest request
    ) {
        List<UserEntity> users = userService.getAll().getContent();
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("users", users);
        return "pages/users";
    }

    @GetMapping("/users/profile")
    public String usersOneGet(
            Model model,
            HttpServletRequest request,
            Authentication authentication
    ) {
        UserEntity user = userService.getByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        final Set<UserRole> roles = user.getAuthorities();
        UserModel um = new UserModel();
        user.setId(user.getId());
        um.setUsername(user.getUsername());
        um.setPassword("");
        um.setImages(user.getImages().stream().map(imageMapper::toDto).toList());

        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("user", um);

//        model.addAttribute("accessToAdminPanel", roles.contains(UserRole.ROLE_SYS_ADMIN));
//        model.addAttribute("serviceEditor", roles.contains(UserRole.ROLE_SERVICE_EDITOR));
//        model.addAttribute("imageEditor", roles.contains(UserRole.ROLE_IMAGE_EDITOR));
//        model.addAttribute("employeeEditor", roles.contains(UserRole.ROLE_EMPLOYEE_EDITOR));
//        model.addAttribute("appointmentEditor", roles.contains(UserRole.ROLE_APPOINTMENT_EDITOR));
//        model.addAttribute("accountEditor", roles.contains(UserRole.ROLE_ACCOUNT_EDITOR));
        return "pages/profile";
    }

    @PostMapping("/users/profile")
    public String usersProfilePost(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "accessToAdminPanel", defaultValue = "false") boolean accessToAdminPanel,
            @RequestParam(value = "serviceEditor",defaultValue = "false") boolean serviceEditor,
            @RequestParam(value = "imageEditor",defaultValue = "false") boolean imageEditor,
            @RequestParam(value = "employeeEditor",defaultValue = "false") boolean employeeEditor,
            @RequestParam(value = "appointmentEditor",defaultValue = "false") boolean appointmentEditor,
            @RequestParam(value = "accountEditor",defaultValue = "false") boolean accountEditor,
            BindingResult result
    ) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addError(new ObjectError("username", "Введіть ім'я користувача"));
        }
        if (result.hasErrors()) {
            return "pages/profile";
        }

        final Set<UserRole> roles = user.getAuthorities();
        if (accessToAdminPanel) {
            roles.add(UserRole.ROLE_SYS_ADMIN);
        }
        if (serviceEditor) {
            roles.add(UserRole.ROLE_SERVICE_EDITOR);
        }
        if (imageEditor) {
            roles.add(UserRole.ROLE_IMAGE_EDITOR);
        }
        if (employeeEditor) {
            roles.add(UserRole.ROLE_EMPLOYEE_EDITOR);
        }
        if (appointmentEditor) {
            roles.add(UserRole.ROLE_APPOINTMENT_EDITOR);
        }
        if (accountEditor) {
            roles.add(UserRole.ROLE_ACCOUNT_EDITOR);
        }

        userService.update(user.getUsername(), user);

        return "redirect:/users/profile";
    }


    @GetMapping("/users/create")
    public String usersCreateGet(
            Model model
    ) {
        final List<String> roles = Arrays.stream(UserRole.values())
                .map(UserRole::getSimpleName)
                .toList();
        model.addAttribute("user", new UserEntity());
        model.addAttribute("roles", roles);
        return "pages/user-create";
    }


    @PostMapping("/users/create")
    public String usersCreatePost(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "accessToAdminPanel", defaultValue = "false") boolean accessToAdminPanel,
            @RequestParam(value = "serviceEditor",defaultValue = "false") boolean serviceEditor,
            @RequestParam(value = "imageEditor",defaultValue = "false") boolean imageEditor,
            @RequestParam(value = "employeeEditor",defaultValue = "false") boolean employeeEditor,
            @RequestParam(value = "appointmentEditor",defaultValue = "false") boolean appointmentEditor,
            @RequestParam(value = "accountEditor",defaultValue = "false") boolean accountEditor,
            BindingResult result
    ) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addError(new ObjectError("username", "Введіть ім'я користувача"));
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addError(new ObjectError("password", "Введіть пароль"));
        }
        if (result.hasErrors()) {
            return "pages/user-create";
        }

        final Set<UserRole> roles = new HashSet<>();
        if (accessToAdminPanel) {
            roles.add(UserRole.ROLE_SYS_ADMIN);
        }
        if (serviceEditor) {
            roles.add(UserRole.ROLE_SERVICE_EDITOR);
        }
        if (imageEditor) {
            roles.add(UserRole.ROLE_IMAGE_EDITOR);
        }
        if (employeeEditor) {
            roles.add(UserRole.ROLE_EMPLOYEE_EDITOR);
        }
        if (appointmentEditor) {
            roles.add(UserRole.ROLE_APPOINTMENT_EDITOR);
        }
        if (accountEditor) {
            roles.add(UserRole.ROLE_ACCOUNT_EDITOR);
        }

        userService.createWithRoles(user, roles);
        return "redirect:/panel/users";
    }

    @GetMapping("/users/delete")
    public String usersDeleteGet(
            @RequestParam("username") String username
    ) {
        userService.deleteByUsername(username);
        return "redirect:/panel/users";
    }

}
