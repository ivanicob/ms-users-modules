package com.ivanicob.userservice.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusChangeDTO {
    Long id;

    String productName;

    String authorEmailAddress;

    String authorFullName;
}
