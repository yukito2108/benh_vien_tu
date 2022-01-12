package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Positive;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionForm {
    @NotNull(message = "not-null")
    private List<@Valid Specific> medicine;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Specific {
        @Positive
        private String id;
        @Size(max = 255, message = "size-0-255")
        private String value;
    }
}
