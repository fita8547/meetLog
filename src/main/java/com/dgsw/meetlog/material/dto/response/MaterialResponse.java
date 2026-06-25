package com.dgsw.meetlog.material.dto.response;

import com.dgsw.meetlog.material.entity.Material;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MaterialResponse {

    private Integer Id;
    private String fileName;
    private String fileUrl;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MaterialResponse from(Material material) {
        return MaterialResponse.builder()
                .Id(Math.toIntExact(Long.valueOf(material.getId())))
                .fileName(material.getFileName())
                .fileUrl(material.getFileUrl())
                .description(material.getDescription())
                .createdAt(material.getCreatedAt())
                .updatedAt(material.getUpdatedAt())
                .build();
    }
}
