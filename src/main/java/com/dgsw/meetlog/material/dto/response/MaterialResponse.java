package com.dgsw.meetlog.material.dto.response;

import com.dgsw.meetlog.material.entity.Material;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MaterialResponse {

    private Integer userId;
    private String fileName;
    private String fileUrl;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MaterialResponse from(Material material) {
        return MaterialResponse.builder()
                .userId(material.getUserId())
                .fileName(material.getFileName())
                .fileUrl(material.getFileUrl())
                .description(material.getDescription())
                .createdAt(material.getCreatedAt())
                .updatedAt(material.getUpdatedAt())
                .build();
    }
}
