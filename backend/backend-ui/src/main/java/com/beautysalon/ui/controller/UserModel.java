package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.domain.entities.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserModel {
        private Long id;
        private String username;
        private String password;
        private String confirmPassword;
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

        public UserModel(Long id, String username, String password, String confirmPassword, boolean accessToAdminPanel, boolean serviceEditor, boolean imageEditor, boolean employeeEditor, boolean appointmentEditor, boolean accountEditor,String imageUrl, List<ImageDto> images) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.confirmPassword = confirmPassword;
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

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRole(UserRole role) {
            if (role == UserRole.ROLE_SERVICE_EDITOR) {
                serviceEditor = true;
            } else if (role == UserRole.ROLE_APPOINTMENT_EDITOR) {
                appointmentEditor = true;
            } else if (role == UserRole.ROLE_EMPLOYEE_EDITOR) {
                employeeEditor = true;
            } else if (role == UserRole.ROLE_ACCOUNT_EDITOR) {
                accountEditor = true;
            } else if (role == UserRole.ROLE_IMAGE_EDITOR) {
                imageEditor = true;
            } else if (role == UserRole.ROLE_SYS_ADMIN) {
                accessToAdminPanel = true;
            }
        }

        public Set<UserRole> getRoles() {
            final Set<UserRole> roles = new HashSet<>();
            if (serviceEditor) {
                roles.add(UserRole.ROLE_SERVICE_EDITOR);
            }
            if (appointmentEditor) {
                roles.add(UserRole.ROLE_APPOINTMENT_EDITOR);
            }
            if (employeeEditor) {
                roles.add(UserRole.ROLE_EMPLOYEE_EDITOR);
            }
            if (accountEditor) {
                roles.add(UserRole.ROLE_ACCOUNT_EDITOR);
            }
            if (imageEditor) {
                roles.add(UserRole.ROLE_IMAGE_EDITOR);
            }
            if (accessToAdminPanel) {
                roles.add(UserRole.ROLE_SYS_ADMIN);
            }
            return roles;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public List<ImageDto> getImages() {
            return images;
        }

        public void setImages(List<ImageDto> images) {
            this.images = images;
        }
    }