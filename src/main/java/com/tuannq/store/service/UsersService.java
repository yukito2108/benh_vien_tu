package com.tuannq.store.service;


import com.tuannq.store.entity.Work;
import com.tuannq.store.entity.Role;
import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.user.customer.CorporateCustomer;
import com.tuannq.store.entity.user.customer.Customer;
import com.tuannq.store.entity.user.customer.RetailCustomer;
import com.tuannq.store.entity.user.provider.Provider;
import com.tuannq.store.model.ChangePasswordForm;
import com.tuannq.store.model.UsersForm;

import java.util.Collection;
import java.util.List;

public interface UsersService {
    /*
     * Users
     * */
    boolean usersExists(String userName);

    Users getUsersById(Long usersId);

    Users getUsersByUsername(String userName);

    List<Users> getUsersByRoleName(String roleName);

    List<Users> getAllUsers();

    void deleteUsersById(Long usersId);

    void updateUsersPassword(ChangePasswordForm passwordChangeForm);

    /*
     * Provider
     * */
    Provider getProviderById(Long providerId);

    List<Provider> getProvidersWithRetailWorks();

    List<Provider> getProvidersWithCorporateWorks();

    List<Provider> getProvidersByWork(Work work);

    List<Provider> getAllProviders();

    void saveNewProvider(UsersForm usersForm);

    void updateProviderProfile(UsersForm updateData);

    Collection<Role> getRolesForProvider();

    /*
     * Customer
     * */
    Customer getCustomerById(Long customerId);

    List<Customer> getAllCustomers();

    /*
     * RetailCustomer
     * */
    RetailCustomer getRetailCustomerById(Long retailCustomerId);

    void saveNewRetailCustomer(UsersForm usersForm);

    void updateRetailCustomerProfile(UsersForm updateData);

    Collection<Role> getRolesForRetailCustomer();

    /*
     * CorporateCustomer
     * */
    CorporateCustomer getCorporateCustomerById(Long corporateCustomerId);

    List<RetailCustomer> getAllRetailCustomers();

    void saveNewCorporateCustomer(UsersForm usersForm);

    void updateCorporateCustomerProfile(UsersForm updateData);

    Collection<Role> getRoleForCorporateCustomers();


}

