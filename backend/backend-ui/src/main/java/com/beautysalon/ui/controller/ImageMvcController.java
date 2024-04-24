package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.ImageMapper;
import com.beautysalon.domain.entities.Employee;
import com.beautysalon.utils.EmployeeFilter;
import com.beautysalon.domain.entities.Image;
import com.beautysalon.domain.services.EmployeeService;
import com.beautysalon.domain.services.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/panel")
public class ImageMvcController {

    public static class ImageFilter {
        private String group;
        private String username;

        public ImageFilter() {
            this.group = "*";
            this.username = "";
        }

        public ImageFilter(String group, String username) {
            this.group = group;
            this.username = username;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ImageFilter that = (ImageFilter) o;
            return Objects.equals(group, that.group) && Objects.equals(username, that.username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(group, username);
        }
    }

    private final ImageService imageService;
    private final EmployeeService employeeService;
    private final ImageMapper imageMapper;

    public ImageMvcController(
            ImageService imageService,
            EmployeeService employeeService,
            ImageMapper imageMapper
    ) {
        this.imageService = imageService;
        this.employeeService = employeeService;
        this.imageMapper = imageMapper;
    }

    @GetMapping("/images")
    public String imagesGet(
            Model model,
            HttpServletRequest request,
            @ModelAttribute ImageFilter filter
    ) {
        final String username = filter.getUsername();
        final String group = filter.getGroup();
        final List<Employee> employees = employeeService.getAllEmployees(new EmployeeFilter())
                .getContent();
        final String g = (!username.isEmpty() && "emp".equals(group))
                ? String.join("/", group, username)
                : group;
        final List<ImageDto> images = imageService.getAll(g).stream()
                .map(imageMapper::toDto)
                .toList();
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("images", images);
        model.addAttribute("employees", employees);
        model.addAttribute("filter", filter);
        return "pages/images";
    }

    @GetMapping("/images/{filename}")
    private ResponseEntity<?> imageOneGet(
            @PathVariable String filename
    ) {
        String s = filename.replace("+", "/");
        Image image = imageService.get(s);
        return ResponseEntity.ok()
                .header("File-Name", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }


    @GetMapping("/images/create")
    public String imagesCreateGet(
            Model model,
            HttpServletRequest request
    ) {
        final List<Employee> employees = employeeService.getAllEmployees(new EmployeeFilter())
                .getContent();
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("employees", employees);
        return "pages/image-create";
    }

    @PostMapping("/images/create")
    public String imagesPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            @RequestParam(value = "filename", required = false, defaultValue = "") String filename,
            @RequestParam(value = "username", required = false, defaultValue = "") String username
    ) throws IOException {
        imageService.store(file, getFullName(path, username, filename, file.getName()));
        return "redirect:/panel/images";
    }

    @GetMapping("/images/delete")
    public String imagesDelete(
            @RequestParam("filename") String filename
    ) {
        imageService.delete(filename);
        return "redirect:/panel/images";
    }

    private String getFullName(String path, String username, String filename, String defaultFilename) {
        final StringBuilder builder = new StringBuilder(path);
        if (!builder.isEmpty()) {
            builder.append('/');
            if (!username.isBlank()) {
                builder.append(username);
                builder.append('/');
            }
        }
        if (!filename.isBlank()) {
            builder.append(filename);
        } else {
            builder.append(defaultFilename);
        }
        return builder.toString();
    }

}
