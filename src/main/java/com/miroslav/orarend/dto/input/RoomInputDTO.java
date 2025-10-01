package com.miroslav.orarend.dto.input;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoomInputDTO {

    @NotEmpty
    private String name;

}
