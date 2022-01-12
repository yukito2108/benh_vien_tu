package com.tuannq.store.entity;

import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.model.UsersForm;
import com.tuannq.store.model.request.UpdateProfileForm;
import com.tuannq.store.model.request.UserFormAdmin;
import com.tuannq.store.model.request.UserFormCustomer;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
//@MappedSuperclass
public class Users extends BaseEntity {
    @Column(name = "username")
    private String userName;
    @Column(nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false)
    private int gender;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String phone;
    @Column(nullable = false, length = 511)
    private String address;
    @Column(nullable = true, length = 511)
    private String coverImage;


    @OneToMany(mappedBy = "users")
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public Users(Users users, Collection<Role> roles) {
        this.firstName = users.getFirstName();
        this.gender = users.getGender();
        this.email = users.getEmail();
        this.password = users.password;
        this.phone = users.getPhone();
        this.address = users.getAddress();
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return this.roles
                .stream()
                .map(a -> a.getName())
                .collect(Collectors.toSet());
    }

    public void setUser(UserFormAdmin form) {
        this.firstName = form.getFirstName();
        this.gender = Integer.parseInt(form.getGender());
        this.email = form.getEmail();
        this.phone = form.getPhone();
        this.address = form.getAddress();
        this.roles = form.getRoles();
        this.setIsDeleted(form.getIsDeleted());
    }

    public void setUser(UserFormCustomer form) {
        this.firstName = form.getFirstName();
        this.gender = Integer.parseInt(form.getGender());
        this.email = form.getEmail();
        this.phone = form.getPhone();
        this.address = form.getAddress();
    }

    public void setUser(UpdateProfileForm form) {
        this.firstName = form.getFirstName();
        this.gender = Integer.parseInt(form.getGender());
        this.email = form.getEmail();
        this.phone = form.getPhone();
        this.address = form.getAddress();
    }

    public Users(UserFormAdmin form, String password) {
        this.firstName = form.getFirstName();
        this.gender = Integer.parseInt(form.getGender());
        this.email = form.getEmail();
        this.password = password;
        this.phone = form.getPhone();
        this.address = form.getAddress();
        this.roles = form.getRoles();
    }

    public Users(UserFormCustomer form, String password, Collection<Role> roles) {
        this.firstName = form.getFirstName();
        this.gender = Integer.parseInt(form.getGender());
        this.email = form.getEmail();
        this.password = password;
        this.phone = form.getPhone();
        this.address = form.getAddress();
        this.roles = roles;
    }

    public Users(UsersForm newUsersForm, String encryptedPassword, Collection<Role> roles) {
        this.setUserName(newUsersForm.getUserName());
        this.setFirstName(newUsersForm.getFirstName());
        this.setLastName(newUsersForm.getLastName());
        this.setEmail(newUsersForm.getEmail());
        this.setAddress(newUsersForm.getAddress());
        this.setPhone(newUsersForm.getPhone());
        this.password = encryptedPassword;
        this.roles = roles;
    }

    public void update(UsersForm updateData) {
        this.setEmail(updateData.getEmail());
        this.setFirstName(updateData.getFirstName());
        this.setLastName(updateData.getLastName());
        this.setPhone(updateData.getPhone());
        this.setAddress(updateData.getAddress());
    }


    public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users user = (Users) o;
        return this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
