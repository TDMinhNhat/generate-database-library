package dev.tdminhnhat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EntityInformation {
    private String className;
    private String numberOfFields;
    private String numberOfColumns;
    private Boolean isEntity;
    private String packageName;
    private List<EntityInformation> foreignEntities;
}
