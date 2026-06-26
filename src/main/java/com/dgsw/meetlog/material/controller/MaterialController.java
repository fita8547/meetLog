package com.dgsw.meetlog.material.controller;

import com.dgsw.meetlog.material.dto.request.MaterialCreateRequest;
import com.dgsw.meetlog.material.dto.response.MaterialResponse;
import com.dgsw.meetlog.material.service.MaterialService;
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
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponse createMaterial(@RequestBody MaterialCreateRequest request) {
        return materialService.createMaterial(request);
    }

    @GetMapping
    public List<MaterialResponse> getMaterials() {
        return materialService.getMaterials();
    }
}
