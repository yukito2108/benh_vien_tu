package com.tuannq.store.model;


import com.tuannq.store.validation.CurrentPasswordMatches;
import com.tuannq.store.validation.FieldsMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsMatches(field = "password", matchingField = "matchingPassword")
@CurrentPasswordMatches()
public class ChangePasswordForm {


    @NotNull
    private Long id;

    @Size(min = 5, max = 10, message = "Password should have 5-15 letters")
    @NotBlank()
    private String password;

    @Size(min = 5, max = 10, message = "Password should have 5-15 letters")
    @NotBlank()
    private String matchingPassword;

    private String currentPassword;

    public ChangePasswordForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
