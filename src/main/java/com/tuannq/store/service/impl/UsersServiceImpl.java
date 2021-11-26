package com.tuannq.store.service.impl;

import com.tuannq.store.dao.user.customer.CorporateCustomerRepository;
import com.tuannq.store.dao.user.customer.CustomerRepository;
import com.tuannq.store.dao.user.customer.RetailCustomerRepository;
import com.tuannq.store.dao.user.provider.ProviderRepository;
import com.tuannq.store.entity.Work;
import com.tuannq.store.entity.WorkingPlan;
import com.tuannq.store.entity.Role;
import com.tuannq.store.entity.Users;
import com.tuannq.store.entity.user.customer.CorporateCustomer;
import com.tuannq.store.entity.user.customer.Customer;
import com.tuannq.store.entity.user.customer.RetailCustomer;
import com.tuannq.store.entity.user.provider.Provider;
import com.tuannq.store.model.ChangePasswordForm;
import com.tuannq.store.model.UsersForm;
import com.tuannq.store.repository.RoleRepository;
import com.tuannq.store.repository.UsersRepository;
import com.tuannq.store.service.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final ProviderRepository providerRepository;
    private final CustomerRepository customerRepository;
    private final CorporateCustomerRepository corporateCustomerRepository;
    private final RetailCustomerRepository retailCustomerRepository;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(ProviderRepository providerRepository, CustomerRepository customerRepository, CorporateCustomerRepository corporateCustomerRepository, RetailCustomerRepository retailCustomerRepository, UsersRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.providerRepository = providerRepository;
        this.customerRepository = customerRepository;
        this.corporateCustomerRepository = corporateCustomerRepository;
        this.retailCustomerRepository = retailCustomerRepository;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean usersExists(String userName) {
        return usersRepository.findByUserName(userName).isPresent();
    }

    @Override
    @PreAuthorize("#usersId == principal.id")
    public Users getUsersById(Long usersId) {
        return usersRepository.findById(Long.valueOf(usersId))
                .orElseThrow(() -> new UsernameNotFoundException("Users not found"));
    }

    @Override
    @PreAuthorize("#customerId == principal.id or hasRole('ADMIN')")
    public Customer getCustomerById(Long customerId) {
        return customerRepository.getOne(customerId);
    }

    @Override
    public Provider getProviderById(Long providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new UsernameNotFoundException("Users not found!"));
    }

    @Override
    @PreAuthorize("#retailCustomerId == principal.id or hasRole('ADMIN')")
    public RetailCustomer getRetailCustomerById(Long retailCustomerId) {
        return retailCustomerRepository.findById(retailCustomerId)
                .orElseThrow(() -> new UsernameNotFoundException("Users not found!"));

    }

    @Override
    @PreAuthorize("#corporateCustomerId == principal.id or hasRole('ADMIN')")
    public CorporateCustomer getCorporateCustomerById(Long corporateCustomerId) {
        return corporateCustomerRepository.findById(corporateCustomerId)
                .orElseThrow(() -> new UsernameNotFoundException("Users not found!"));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<RetailCustomer> getAllRetailCustomers() {
        return retailCustomerRepository.findAll();
    }


    @Override
    public Users getUsersByUsername(String userName) {
        return usersRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Users not found!"));
    }

    @Override
    public List<Users> getUsersByRoleName(String roleName) {
        return usersRepository.findByRoleName(roleName);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUsersById(Long usersId) {
        usersRepository.deleteById(Long.valueOf(usersId));
    }

    @Override
    public List<Provider> getProvidersWithRetailWorks() {
        return providerRepository.findAllWithRetailWorks();
    }

    @Override
    public List<Provider> getProvidersWithCorporateWorks() {
        return providerRepository.findAllWithCorporateWorks();
    }

    @Override
    public List<Provider> getProvidersByWork(Work work) {
        return providerRepository.findByWorks(work);
    }

    @Override
    @PreAuthorize("#passwordChangeForm.id == principal.id")
    public void updateUsersPassword(ChangePasswordForm passwordChangeForm) {
        Users users = usersRepository.getOne(Long.valueOf(passwordChangeForm.getId()));
        users.setPassword(passwordEncoder.encode(passwordChangeForm.getPassword()));
        usersRepository.save(users);
    }

    @Override
    @PreAuthorize("#updateData.id == principal.id or hasRole('ADMIN')")
    public void updateProviderProfile(UsersForm updateData) {
        Provider provider = providerRepository.getOne(updateData.getId());
        provider.update(updateData);
        providerRepository.save(provider);
    }

    @Override
    @PreAuthorize("#updateData.id == principal.id or hasRole('ADMIN')")
    public void updateRetailCustomerProfile(UsersForm updateData) {
        RetailCustomer retailCustomer = retailCustomerRepository.getOne(updateData.getId());
        retailCustomer.update(updateData);
        retailCustomerRepository.save(retailCustomer);

    }

    @Override
    @PreAuthorize("#updateData.id == principal.id or hasRole('ADMIN')")
    public void updateCorporateCustomerProfile(UsersForm updateData) {
        CorporateCustomer corporateCustomer = corporateCustomerRepository.getOne(updateData.getId());
        corporateCustomer.update(updateData);
        corporateCustomerRepository.save(corporateCustomer);

    }

    @Override
    public void saveNewRetailCustomer(UsersForm usersForm) {
        RetailCustomer retailCustomer = new RetailCustomer(usersForm, passwordEncoder.encode(usersForm.getPassword()), getRolesForRetailCustomer());
        retailCustomerRepository.save(retailCustomer);
    }

    @Override
    public void saveNewCorporateCustomer(UsersForm usersForm) {
        CorporateCustomer corporateCustomer = new CorporateCustomer(usersForm, passwordEncoder.encode(usersForm.getPassword()), getRoleForCorporateCustomers());
        corporateCustomerRepository.save(corporateCustomer);
    }

    @Override
    public void saveNewProvider(UsersForm usersForm) {
        WorkingPlan workingPlan = WorkingPlan.generateDefaultWorkingPlan();
        Provider provider = new Provider(usersForm, passwordEncoder.encode(usersForm.getPassword()), getRolesForProvider(), workingPlan);
        providerRepository.save(provider);
    }

    @Override
    public Collection<Role> getRolesForRetailCustomer() {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER_RETAIL"));
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));
        return roles;
    }


    @Override
    public Collection<Role> getRoleForCorporateCustomers() {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER_CORPORATE"));
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));
        return roles;
    }

    @Override
    public Collection<Role> getRolesForProvider() {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_PROVIDER"));
        return roles;
    }


}

