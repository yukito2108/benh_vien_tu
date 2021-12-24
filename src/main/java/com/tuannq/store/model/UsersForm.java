package com.tuannq.store.model;

import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.Work;
import com.tuannq.store.entity.user.provider.Provider;
import com.tuannq.store.validation.FieldsMatches;
//import com.tuannq.store.validation.UniqueUsername;
import com.tuannq.store.validation.groups.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@FieldsMatches(field = "password", matchingField = "matchingPassword", groups = {CreateUsers.class})
@Data
@AllArgsConstructor
public class UsersForm {

    @NotNull(groups = {UpdateUsers.class})
    @Min(value = 1, groups = {UpdateUsers.class})
    private Long id;

//    @UniqueUsername(groups = {CreateUsers.class})
    @Size(min = 5, max = 15, groups = {CreateUsers.class}, message = "Username should have 5-15 letters")
    @NotBlank(groups = {CreateUsers.class})
    private String userName;

    @Size(min = 5, max = 15, groups = {CreateUsers.class}, message = "Password should have 5-15 letters")
    @NotBlank(groups = {CreateUsers.class})
    private String password;

    @Size(min = 5, max = 15, groups = {CreateUsers.class}, message = "Password should have 5-15 letters")
    @NotBlank(groups = {CreateUsers.class})
    private String matchingPassword;

    @NotBlank(groups = {CreateUsers.class, UpdateUsers.class}, message = "First name cannot be empty")
    private String firstName;

    @NotBlank(groups = {CreateUsers.class, UpdateUsers.class}, message = "Last name cannot be empty")
    private String lastName;

    @Email(groups = {CreateUsers.class, UpdateUsers.class}, message = "Email not valid!")
    @NotBlank(groups = {CreateUsers.class, UpdateUsers.class}, message = "Email cannot be empty")
    private String email;

    @Pattern(groups = {CreateUsers.class, UpdateUsers.class}, regexp = "[0-9]{9}", message = "Please enter valid mobile phone")
    @NotBlank(groups = {CreateUsers.class, UpdateUsers.class}, message = "Mobile phone cannot be empty")
    private String phone;

    @Size(groups = {CreateUsers.class, UpdateUsers.class}, min = 5, max = 30, message = "Wrong street!")
    @NotBlank(groups = {CreateUsers.class, UpdateUsers.class}, message = "Street cannot be empty")
    private String address;

    @NotBlank(groups = {CreateUsers.class, UpdateUsers.class}, message = "Cover image cannot be empty")
    private String coverImage;

    /*
     * CorporateCustomer only:
     * */
    @NotBlank(groups = {CreateCorporateCustomer.class, UpdateCorporateCustomer.class}, message = "Company cannot be empty")
    private String companyName;

    @Pattern(groups = {CreateCorporateCustomer.class, UpdateCorporateCustomer.class}, regexp = "[0-9]{10}", message = "Please enter valid Polish VAT number")
    @NotBlank(groups = {CreateCorporateCustomer.class, UpdateCorporateCustomer.class}, message = "VAT number cannot be empty")
    private String vatNumber;

    /*
     * Provider only:
     * */
    @NotNull(groups = {CreateProvider.class, UpdateProvider.class})
    private List<Work> works;


    public UsersForm() {
    }

    public UsersForm(Users users) {
        this.setId(users.getId());
        this.setUserName(users.getUserName());
        this.setFirstName(users.getFirstName());
        this.setLastName(users.getLastName());
        this.setEmail(users.getEmail());
        this.setAddress(users.getAddress());
        this.setPhone(users.getPhone());
        this.setCoverImage(users.getCoverImage());
    }

    public UsersForm(Provider provider) {
        this((Users) provider);
        this.setWorks(provider.getWorks());
    }

//    public UsersForm(RetailCustomer retailCustomer) {
//        this((Users) retailCustomer);
//    }
//
//    public UsersForm(CorporateCustomer corporateCustomer) {
//        this((Users) corporateCustomer);
//        this.setCompanyName(corporateCustomer.getCompanyName());
//        this.setVatNumber(corporateCustomer.getVatNumber());
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
