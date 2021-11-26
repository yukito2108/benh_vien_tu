package com.tuannq.store.entity.user.customer;

import com.tuannq.store.entity.Role;
import com.tuannq.store.model.UsersForm;

import javax.persistence.*;
import java.util.Collection;
//@MappedSuperclass
@Entity
@Table(name = "corporate_customers")
@PrimaryKeyJoinColumn(name = "id_customer")
public class CorporateCustomer extends Customer {

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "vat_number")
    private String vatNumber;


    public CorporateCustomer() {
    }

    public CorporateCustomer(UsersForm usersFormDTO, String encryptedPassword, Collection<Role> roles) {
        super(usersFormDTO, encryptedPassword, roles);
        this.companyName = usersFormDTO.getCompanyName();
        this.vatNumber = usersFormDTO.getVatNumber();
    }

    @Override
    public void update(UsersForm updateData) {
        super.update(updateData);
        this.companyName = updateData.getCompanyName();
        this.vatNumber = updateData.getVatNumber();
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

}
