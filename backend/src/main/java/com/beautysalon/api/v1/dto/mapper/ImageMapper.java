package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.api.v1.utils.PathUtils;
import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.api.v1.dto.mapper.base.Mapper;
import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.services.ApiProperties;

import java.nio.file.Path;

@Mapper
public class ImageMapper extends AbstractMapper<Image, ImageDto> {
    private final ApiProperties apiProp;

    public ImageMapper(ApiProperties apiProp) {
        super();
        this.apiProp = apiProp;
    }

    @Override
    protected void copyDto(Image source, ImageDto destination) {
        Path path = Path.of(source.getName());
        String[] fileData = PathUtils.splitPathFilename(path);

        destination.setPath(fileData[0]);
        destination.setName(fileData[1]);
        destination.setFullName(path.toString());
        destination.setUrl(String.format("%s/images/%s",
                apiProp.getBaseUrl(),
                PathUtils.normalizeUrlPath(source.getName())
        ));
    }
}
