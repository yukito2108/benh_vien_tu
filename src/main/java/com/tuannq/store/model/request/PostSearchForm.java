package com.tuannq.store.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchForm {
    private String title = "";
    private String status = "";
    private Integer page = 1;
    private String order = "";
    private String direction = "";
}
