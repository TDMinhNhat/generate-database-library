package io.github.tdminhnhat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class contain the information of the class use for showing in table on GUI.
 * @author Nhat Truong
 * @version 1.1.0
 * @since 0.0.1-beta
 */
@Data
@AllArgsConstructor
public class EntityInformation {

    /**
     * The class name
     */
    private String className;

    /**
     * The subclass name. Normally, the class which extends another class.
     */
    private String subClassName;

    /**
     * The package name of the class where the class created in that package.
     */
    private String packageName;

    /**
     * The number of fields of the class.
     */
    private Long numberOfFields;

    /**
     * The class is annotated with @Entity or not
     */
    private Boolean isEntity;

    /**
     * Count the number of foreign keys from the class.
     */
    private Long countFK;
}
