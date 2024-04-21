package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.api.v1.utils.PathUtils;
import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.domain.entities.Image;
import com.beautysalon.domain.services.ApiProperties;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper extends AbstractMapper<Image, ImageDto> {

    private final ApiProperties apiProp;

    public ImageMapper(ApiProperties apiProp) {
        super();
        this.apiProp = apiProp;
    }

    @Override
    protected void postDtoCopy(Image source, ImageDto destination) {
        destination.setUrl(String.format("%s/images/%s",
                apiProp.getBaseUrl(),
                PathUtils.normalizeForUrl(source.getFullName())
        ));
    }
}
