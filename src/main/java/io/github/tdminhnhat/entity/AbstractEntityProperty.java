package io.github.tdminhnhat.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

/**
 * Show the default fields of the entity. The other classes duplicate with these fields in this class can be use this class like a subclass.
 * @version 1.1.0
 * @since 0.0.1-beta
 */
@MappedSuperclass
@Getter @Setter
@NoArgsConstructor
public abstract class AbstractEntityProperty {

    /**
     * The field describe this data is deleted or not
     */
    @Column(name = "delete_flag", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleteFlag;

    /**
     * The date when this data created
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    /**
     * The date when this data updated
     */
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    /**
     * Who created this data
     */
    @Column(name = "created_by", length = 100, nullable = false, updatable = false)
    private String createdBy;

    /**
     * Who updated this data
     */
    @Column(name = "updated_by", length = 100, nullable = false)
    private String updatedBy;

    /**
     * The version of the data. Modifying will increase the number version.
     */
    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public AbstractEntityProperty(String createdBy, String updatedBy) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public void onPrePersist() {
        createdAt = ZonedDateTime.now();
        deleteFlag = false;
    }

    public void onPreUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
