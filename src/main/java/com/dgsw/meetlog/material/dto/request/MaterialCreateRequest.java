package com.dgsw.meetlog.material.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MaterialCreateRequest {

    private String fileName;
    private String fileUrl;
    private String description;
}
