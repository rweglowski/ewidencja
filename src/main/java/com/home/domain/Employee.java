package com.home.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.home.domain.enumeration.EmployeeStatus;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmployeeStatus status;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    private Set<Asset> assets = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    private Set<Ot> ots = new HashSet<>();

    @OneToMany(mappedBy = "forwarder")
    @JsonIgnore
    private Set<Pt> forwarders = new HashSet<>();

    @ManyToMany(mappedBy = "recipients")
    @JsonIgnore
    private Set<Pt> ptRecipients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public Employee surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Employee status(EmployeeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public Employee assets(Set<Asset> assets) {
        this.assets = assets;
        return this;
    }

    public Employee addAsset(Asset asset) {
        this.assets.add(asset);
        asset.getEmployees().add(this);
        return this;
    }

    public Employee removeAsset(Asset asset) {
        this.assets.remove(asset);
        asset.getEmployees().remove(this);
        return this;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public Set<Ot> getOts() {
        return ots;
    }

    public Employee ots(Set<Ot> ots) {
        this.ots = ots;
        return this;
    }

    public Employee addOt(Ot ot) {
        this.ots.add(ot);
        ot.getEmployees().add(this);
        return this;
    }

    public Employee removeOt(Ot ot) {
        this.ots.remove(ot);
        ot.getEmployees().remove(this);
        return this;
    }

    public void setOts(Set<Ot> ots) {
        this.ots = ots;
    }

    public Set<Pt> getForwarders() {
        return forwarders;
    }

    public Employee forwarders(Set<Pt> pts) {
        this.forwarders = pts;
        return this;
    }

    public Employee addForwarder(Pt pt) {
        this.forwarders.add(pt);
        pt.setForwarder(this);
        return this;
    }

    public Employee removeForwarder(Pt pt) {
        this.forwarders.remove(pt);
        pt.setForwarder(null);
        return this;
    }

    public void setForwarders(Set<Pt> pts) {
        this.forwarders = pts;
    }

    public Set<Pt> getPtRecipients() {
        return ptRecipients;
    }

    public Employee ptRecipients(Set<Pt> pts) {
        this.ptRecipients = pts;
        return this;
    }

    public Employee addPtRecipients(Pt pt) {
        this.ptRecipients.add(pt);
        pt.getRecipients().add(this);
        return this;
    }

    public Employee removePtRecipients(Pt pt) {
        this.ptRecipients.remove(pt);
        pt.getRecipients().remove(this);
        return this;
    }

    public void setPtRecipients(Set<Pt> pts) {
        this.ptRecipients = pts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if (employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", surname='" + surname + "'" +
            ", email='" + email + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
