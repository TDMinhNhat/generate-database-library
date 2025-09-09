package io.github.tdminhnhat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EntityInformation {
    private String className;
    private String subClassName;
    private String packageName;
    private Long numberOfFields;
    private Boolean isEntity;
    private Long countFK;
}
