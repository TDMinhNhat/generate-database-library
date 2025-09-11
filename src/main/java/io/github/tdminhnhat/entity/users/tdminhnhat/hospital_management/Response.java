package io.github.tdminhnhat.entity.users.tdminhnhat.hospital_management;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private Integer code;
    private String message;
    private Object data;
}
