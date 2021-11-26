package com.tuannq.store.entity.user.provider;

import com.tuannq.store.entity.Appointment;
import com.tuannq.store.entity.Work;
import com.tuannq.store.entity.WorkingPlan;
import com.tuannq.store.entity.Role;
import com.tuannq.store.entity.Users;
import com.tuannq.store.model.UsersForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "providers")
@PrimaryKeyJoinColumn(name = "id_provider")
//@MappedSuperclass
public class Provider extends Users {

    @OneToMany(mappedBy = "provider")
    private List<Appointment> appointments;

    @ManyToMany
    @JoinTable(name = "works_providers", joinColumns = @JoinColumn(name = "id_users"), inverseJoinColumns = @JoinColumn(name = "id_work"))
    private List<Work> works;

    @OneToOne(mappedBy = "provider",cascade = CascadeType.ALL)
    private WorkingPlan workingPlan;

    public Provider() {
    }

    public Provider(UsersForm usersFormDTO, String encryptedPassword, Collection<Role> roles, WorkingPlan workingPlan) {
        super(usersFormDTO, encryptedPassword, roles);
        this.workingPlan = workingPlan;
        workingPlan.setProvider(this);
        this.works = usersFormDTO.getWorks();
    }

    @Override
    public void update(UsersForm updateData) {
        super.update(updateData);
        this.works = updateData.getWorks();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public WorkingPlan getWorkingPlan() {
        return workingPlan;
    }

    public void setWorkingPlan(WorkingPlan workingPlan) {
        this.workingPlan = workingPlan;
    }

    public List<Work> getCorporateWorks() {
        List<Work> corporateWorks = new ArrayList<>();
        for (Work w : works) {
            if (w.getTargetCustomer().equals("corporate")) {
                corporateWorks.add(w);
            }
        }
        return corporateWorks;
    }

    public List<Work> getRetailWorks() {
        List<Work> retailWorks = new ArrayList<>();
        for (Work w : works) {
            if (w.getTargetCustomer().equals("retail")) {
                retailWorks.add(w);
            }
        }
        return retailWorks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Provider provider = (Provider) o;
        return provider.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointments, works, workingPlan);
    }

}
