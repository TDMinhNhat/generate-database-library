package dev.tdminhnhat.entity.topics.basic.enums;

public enum PersonStatus {
    BLOCKED(0), ACTIVE(1), BLOCK_TEMPORARILY(2);

    private final Integer value;

    PersonStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
