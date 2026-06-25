package com.dgsw.meetlog.material.service;

import com.dgsw.meetlog.material.dto.request.MaterialCreateRequest;
import com.dgsw.meetlog.material.dto.response.MaterialResponse;
import com.dgsw.meetlog.material.entity.Material;
import com.dgsw.meetlog.material.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialResponse createMaterial(MaterialCreateRequest request) {
        Material material = Material.builder()
                .fileName(request.getFileName())
                .fileUrl(request.getFileUrl())
                .description(request.getDescription())
                .build();

        return MaterialResponse.from(materialRepository.save(material));
    }

    public List<MaterialResponse> getMaterials() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialResponse::from)
                .toList();
    }
}
