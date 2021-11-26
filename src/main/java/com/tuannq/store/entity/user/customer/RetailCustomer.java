package com.tuannq.store.entity.user.customer;

import com.tuannq.store.entity.Role;
import com.tuannq.store.model.UsersForm;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Collection;

//@MappedSuperclass
@Entity
@Table(name = "retail_customers")
@PrimaryKeyJoinColumn(name = "id_customer")
public class RetailCustomer extends Customer {

    public RetailCustomer() {
    }

    public RetailCustomer(UsersForm usersFormDTO, String encryptedPassword, Collection<Role> roles) {
        super(usersFormDTO, encryptedPassword, roles);
    }


}
