package com.dgsw.meetlog.material.dto.response;

import com.dgsw.meetlog.material.entity.Material;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MaterialResponse {

    private Long id;
    private String fileName;
    private String fileUrl;
    private String description;

    public static MaterialResponse from(Material material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .fileName(material.getFileName())
                .fileUrl(material.getFileUrl())
                .description(material.getDescription())
                .build();
    }
}
