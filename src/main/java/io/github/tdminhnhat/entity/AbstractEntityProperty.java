package io.github.tdminhnhat.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor
public abstract class AbstractEntityProperty {

    @Column(name = "delete_flag", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleteFlag;

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @Column(name = "created_by", length = 100, nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_by", length = 100, nullable = false)
    private String updatedBy;

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
