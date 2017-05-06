package com.home.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.home.domain.enumeration.AssetStatus;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_code")
    private String registrationCode;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "symbol")
    private String symbol;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "end_date_of_use")
    private LocalDate endDateOfUse;

    @Column(name = "liquidation_date")
    private LocalDate liquidationDate;

    @Column(name = "start_date_of_use")
    private LocalDate startDateOfUse;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AssetStatus status;

    @Column(name = "description")
    private String description;

    @NotNull
    @Min(value = 0)
    @Column(name = "value", nullable = false)
    private Long value;

    @Column(name = "asset_group")
    private String assetGroup;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "inventory_code")
    private String inventoryCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "asset_employee",
               joinColumns = @JoinColumn(name="assets_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="employees_id", referencedColumnName="id"))
    private Set<Employee> employees = new HashSet<>();

    @OneToOne(mappedBy = "asset")
    @JsonIgnore
    private Ot ot;

    @OneToMany(mappedBy = "asset")
    @JsonIgnore
    private Set<Pt> pts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public Asset registrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
        return this;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public Asset barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSymbol() {
        return symbol;
    }

    public Asset symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Asset purchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getEndDateOfUse() {
        return endDateOfUse;
    }

    public Asset endDateOfUse(LocalDate endDateOfUse) {
        this.endDateOfUse = endDateOfUse;
        return this;
    }

    public void setEndDateOfUse(LocalDate endDateOfUse) {
        this.endDateOfUse = endDateOfUse;
    }

    public LocalDate getLiquidationDate() {
        return liquidationDate;
    }

    public Asset liquidationDate(LocalDate liquidationDate) {
        this.liquidationDate = liquidationDate;
        return this;
    }

    public void setLiquidationDate(LocalDate liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

    public LocalDate getStartDateOfUse() {
        return startDateOfUse;
    }

    public Asset startDateOfUse(LocalDate startDateOfUse) {
        this.startDateOfUse = startDateOfUse;
        return this;
    }

    public void setStartDateOfUse(LocalDate startDateOfUse) {
        this.startDateOfUse = startDateOfUse;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public Asset status(AssetStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public Asset description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public Asset value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public Asset assetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
        return this;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getName() {
        return name;
    }

    public Asset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public Asset inventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
        return this;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Asset employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Asset addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getAssets().add(this);
        return this;
    }

    public Asset removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getAssets().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Ot getOt() {
        return ot;
    }

    public Asset ot(Ot ot) {
        this.ot = ot;
        return this;
    }

    public void setOt(Ot ot) {
        this.ot = ot;
    }

    public Set<Pt> getPts() {
        return pts;
    }

    public Asset pts(Set<Pt> pts) {
        this.pts = pts;
        return this;
    }

    public Asset addPt(Pt pt) {
        this.pts.add(pt);
        pt.setAsset(this);
        return this;
    }

    public Asset removePt(Pt pt) {
        this.pts.remove(pt);
        pt.setAsset(null);
        return this;
    }

    public void setPts(Set<Pt> pts) {
        this.pts = pts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asset asset = (Asset) o;
        if (asset.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, asset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + id +
            ", registrationCode='" + registrationCode + "'" +
            ", barcode='" + barcode + "'" +
            ", symbol='" + symbol + "'" +
            ", purchaseDate='" + purchaseDate + "'" +
            ", endDateOfUse='" + endDateOfUse + "'" +
            ", liquidationDate='" + liquidationDate + "'" +
            ", startDateOfUse='" + startDateOfUse + "'" +
            ", status='" + status + "'" +
            ", description='" + description + "'" +
            ", value='" + value + "'" +
            ", assetGroup='" + assetGroup + "'" +
            ", name='" + name + "'" +
            ", inventoryCode='" + inventoryCode + "'" +
            '}';
    }
}
