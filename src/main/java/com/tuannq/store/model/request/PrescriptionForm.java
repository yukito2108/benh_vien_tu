package com.tuannq.store.model.request;

import com.tuannq.store.model.annotation.Positive;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionForm {
    private List<@Valid Specific> medicine = new ArrayList<>();

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Specific {
        @NotNull(message = "not-null")
        @NotEmpty(message = "not-null")
        @Positive
        private String id;

        @NotNull(message = "not-null")
        @NotEmpty(message = "not-null")
        @Size(max = 255, message = "size-0-255")
        private String value;
    }
}
