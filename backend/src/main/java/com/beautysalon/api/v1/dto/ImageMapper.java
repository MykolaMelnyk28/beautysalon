package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.services.StorageProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class ImageMapper {
    private final StorageProperties storageProp;

    public ImageMapper(
            StorageProperties storageProp
    ) {
        this.storageProp = storageProp;
    }

    public ImageDto toDto(Image image) {
        if (image == null) {
            return null;
        }
        Path path = Path.of(image.getName());
        String[] fileData = extractPathFilename(path);

        ImageDto dto = new ImageDto();
        dto.setPath(fileData[0]);
        dto.setName(fileData[1]);
        dto.setFullName(path.toString());

        String url = image.getName().replace("/", "+").replace("\\", "+");
        url = String.join("/", storageProp.getLocation() + "/images", url);
        dto.setUrl(url);

        return dto;
    }

    private String[] extractPathFilename(Path path) {
        Path parent = path.getParent();
        String pathFile = (parent != null) ? parent.toString() : "";
        String filename = path.toFile().getName();
        return new String[] {pathFile, filename};
    }
}
