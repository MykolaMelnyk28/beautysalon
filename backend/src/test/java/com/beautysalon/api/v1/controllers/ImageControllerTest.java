package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1._utils.JSON;
import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.ImageMapper;
import com.beautysalon.domain.entities.Image;
import com.beautysalon.domain.entities.UserEntity;
import com.beautysalon.domain.services.ApiProperties;
import com.beautysalon.domain.services.ImageService;
import com.beautysalon.utils.PathUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImageController.class)
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    private static final String baseUrl = "http://localhost:8080/api/v1";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private ImageMapper imageMapper;

    @MockBean
    private ApiProperties apiProp;

    @Test
    void putShouldReturn201StatusAndCreatedImageIfItNotExists() throws Exception {
        final String imageName = "group1+image1.jpg";
        byte[] content = "test content".getBytes();
        MockMultipartFile file = new MockMultipartFile("image1", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, content);

        when(imageService.exists(any(String.class))).thenReturn(false);
        when(apiProp.getBaseUrl()).thenReturn(baseUrl);

        mvc.perform(put("/v1/images/{imagename}", imageName)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(content))
                .andExpect(status().isCreated());
    }

    @Test
    void putShouldReturn204StatusAndUpdatedImageIfItExists() throws Exception {
        final String imageName = "group1+image1.jpg";
        byte[] content = "test content".getBytes();
        MockMultipartFile file = new MockMultipartFile("image1", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, content);

        when(imageService.exists(any(String.class))).thenReturn(true);
        when(apiProp.getBaseUrl()).thenReturn(baseUrl);

        mvc.perform(put("/v1/images/{imagename}", imageName)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(content))
                .andExpect(status().isNoContent());
    }

    @Test
    void getImageByNameShouldReturn200StatusAndImageAsBinaryStream()
            throws Exception {
        final String imageName = "group1+image1.jpg";
        final Image image = new Image(
                1L,
                "group1",
                "image1.jpg",
                "image1.jpg",
                "group1/image1.jpg",
                3145728L,
                "image/jpeg",
                true,
                new byte[] {1,2,3,4,5},
                new UserEntity()
        );
        byte[] response = image.getBytes();

        when(imageService.get(image.getName())).thenReturn(image);

        mvc.perform(get("/v1/images/{imageName}", imageName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf(image.getContentType())))
                .andExpect(header().string("File-Name", image.getOriginalFileName()))
                .andExpect(header().longValue("Content-Length", image.getSize()))
                .andExpect(content().bytes(response));
    }

    @Test
    void getImagesByPathShouldReturn200StatusAndImageDtoList() throws Exception {
        final List<ImageDto> imageDtos = List.of(
                new ImageDto(
                        String.format("%s/images/%s", baseUrl, "group1+image1.jpg"),
                        "group1/image1.jpg",
                        "image1.jpg",
                        "group1",
                        false
                ),
                new ImageDto(
                        String.format("%s/images/%s", baseUrl, "group1+group1_2+image2.jpg"),
                        "group1/group1_2/image2.jpg",
                        "image2.jpg",
                        "group1/group1_2",
                        false
                )
        );
        List<Image> images = List.of(
                new Image(
                        1L,
                        "image1.jpg",
                        "group1",
                        "group1/image1.jpg",
                        "image1.jpg",
                        3145728L,
                        "image/jpeg",
                        true,
                        new byte[0],
                        new UserEntity()
                ),
                new Image(
                        2L,
                        "image2.jpg",
                        "group1/group1_2",
                        "group1/group1_2/image2.jpg",
                        "image2.jpg",
                        2097152L,
                        "image/jpeg",
                        false,
                        new byte[0],
                        new UserEntity()
                )
        );
        String group = "group1";

        when(imageService.getAll(anyString())).thenReturn(images);
        when(imageMapper.toDto(any(Image.class))).thenReturn(imageDtos.get(0), imageDtos.get(1));

        mvc.perform(get("/v1/images").param("g", group))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON.stringify(imageDtos)));
    }

    @Test
    void deleteByNameShouldReturn204Status() throws Exception {
        String imageFullName = "emp/1/image.jpg";
        String normalizeImageFullName = PathUtils.normalizeForUrl(imageFullName);

        mvc.perform(delete("/v1/images/{imageName}", normalizeImageFullName))
                .andExpect(status().isNoContent());

        verify(imageService, times(1)).delete(normalizeImageFullName);
    }
}