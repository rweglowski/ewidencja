package com.home.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ot.
 */
@Entity
@Table(name = "ot")
public class Ot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "place")
    private String place;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @NotNull
    @Column(name = "provider", nullable = false)
    private String provider;

    @OneToOne
    @JoinColumn(unique = true)
    private Asset asset;

    @ManyToMany
    @JoinTable(name = "ot_employee",
               joinColumns = @JoinColumn(name="ots_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="employees_id", referencedColumnName="id"))
    private Set<Employee> employees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public Ot place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public Ot date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Ot name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public Ot path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProvider() {
        return provider;
    }

    public Ot provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Asset getAsset() {
        return asset;
    }

    public Ot asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Ot employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Ot addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getOts().add(this);
        return this;
    }

    public Ot removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getOts().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ot ot = (Ot) o;
        if (ot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ot{" +
            "id=" + id +
            ", place='" + place + "'" +
            ", date='" + date + "'" +
            ", name='" + name + "'" +
            ", path='" + path + "'" +
            ", provider='" + provider + "'" +
            '}';
    }
}
