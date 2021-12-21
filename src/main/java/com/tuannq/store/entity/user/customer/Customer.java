package com.tuannq.store.entity.user.customer;

import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.Role;
import com.tuannq.store.entity.Users;
import com.tuannq.store.model.UsersForm;
import com.tuannq.store.model.request.UserFormCustomer;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id_customer")
//@MappedSuperclass
public class Customer extends Users {

    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;

    public Customer() {
    }

    public Customer(UsersForm usersFormDTO, String encryptedPassword, Collection<Role> roles) {
        super(usersFormDTO, encryptedPassword, roles);
    }

    public Customer(Users users, Collection<Role> roles) {
        super(users, roles);
    }

    public String getType() {
        if (super.hasRole("ROLE_CUSTOMER_CORPORATE")) {
            return "corporate";
        } else if (super.hasRole("ROLE_CUSTOMER_RETAIL")) {
            return "retail";
        }
        return "";
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
