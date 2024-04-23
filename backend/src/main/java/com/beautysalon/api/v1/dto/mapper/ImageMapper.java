package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.utils.ApiUtils;
import com.beautysalon.utils.PathUtils;
import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.domain.entities.Image;
import com.beautysalon.domain.services.ApiProperties;
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
