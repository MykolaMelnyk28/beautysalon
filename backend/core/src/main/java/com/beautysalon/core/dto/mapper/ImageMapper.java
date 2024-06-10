package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.ImageDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.utils.PathUtils;
import com.beautysalon.core.entities.Image;
import com.beautysalon.core.services.ApiProperties;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper extends AbstractMapper<Image, ImageDto> {

    private final ApiProperties properties;
    public ImageMapper(ApiProperties properties) {
        super();
        this.properties = properties;
    }

    @Override
    protected void postDtoCopy(Image source, ImageDto destination) {
        destination.setUrl(String.format("%s/images/%s",
                properties.getBaseUrl(),
                PathUtils.normalizeForUrl(source.getFullName())
        ));
    }

}
