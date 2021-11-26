package com.tuannq.store.model.dto;

import com.tuannq.store.entity.Role;
import com.tuannq.store.entity.Users;
import lombok.*;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO extends AbstractDTO {
    private String firstName;

    private int gender;

    private String email;

    private String phone;

    private String address;

    private Collection<String> roles;

    public UserDTO(Users user) {
        super(
            user.getId(),
            user.getCreatedBy(),
            user.getCreatedAt(),
            user.getModifiedBy(),
            user.getModifiedAt(),
            user.getIsDeleted()
        );
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.roles = user.getRoles();
        this.gender = user.getGender();
    }
}
