package com.home.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pt.
 */
@Entity
@Table(name = "pt")
public class Pt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "pt_name", nullable = false)
    private String ptName;

    @Column(name = "path")
    private String path;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private Employee forwarder;

    @ManyToMany
    @JoinTable(name = "pt_recipient",
               joinColumns = @JoinColumn(name="pts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="recipients_id", referencedColumnName="id"))
    private Set<Employee> recipients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Pt date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Pt name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPtName() {
        return ptName;
    }

    public Pt ptName(String ptName) {
        this.ptName = ptName;
        return this;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getPath() {
        return path;
    }

    public Pt path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Asset getAsset() {
        return asset;
    }

    public Pt asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Employee getForwarder() {
        return forwarder;
    }

    public Pt forwarder(Employee employee) {
        this.forwarder = employee;
        return this;
    }

    public void setForwarder(Employee employee) {
        this.forwarder = employee;
    }

    public Set<Employee> getRecipients() {
        return recipients;
    }

    public Pt recipients(Set<Employee> employees) {
        this.recipients = employees;
        return this;
    }

    public Pt addRecipient(Employee employee) {
        this.recipients.add(employee);
        employee.getPtRecipients().add(this);
        return this;
    }

    public Pt removeRecipient(Employee employee) {
        this.recipients.remove(employee);
        employee.getPtRecipients().remove(this);
        return this;
    }

    public void setRecipients(Set<Employee> employees) {
        this.recipients = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pt pt = (Pt) o;
        if (pt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pt{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", name='" + name + "'" +
            ", ptName='" + ptName + "'" +
            ", path='" + path + "'" +
            '}';
    }
}
