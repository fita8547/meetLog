package com.dgsw.meetlog.material.controller;

import com.dgsw.meetlog.material.dto.request.MaterialCreateRequest;
import com.dgsw.meetlog.material.dto.response.MaterialResponse;
import com.dgsw.meetlog.material.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
@Tag(name = "Material", description = "첨부파일 및 자료 설명 API")
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "첨부자료 추가", description = "파일명, 파일 URL, 자료 설명을 저장한다.")
    public MaterialResponse createMaterial(@RequestBody MaterialCreateRequest request) {
        return materialService.createMaterial(request);
    }

    @GetMapping
    @Operation(summary = "첨부자료 조회", description = "저장된 첨부자료 목록을 조회한다.")
    public List<MaterialResponse> getMaterials() {
        return materialService.getMaterials();
    }
}
