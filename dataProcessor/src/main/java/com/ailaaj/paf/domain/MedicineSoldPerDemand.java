package com.ailaaj.paf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MedicineSoldPerDemand.
 */
@Entity
@Table(name = "medicine_sold_per_demand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MedicineSoldPerDemand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "medicine_name")
    private String medicineName;

    @Column(name = "medicine_order_quantity")
    private Double medicineOrderQuantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    @Column(name = "issued_quantity")
    private Integer issuedQuantity;

    @Column(name = "return_quantity")
    private Integer returnQuantity;

    @Column(name = "selling_date")
    private Instant sellingDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "medicineSoldPerDemands", "patient" }, allowSetters = true)
    private Demand demand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MedicineSoldPerDemand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicineId() {
        return this.medicineId;
    }

    public MedicineSoldPerDemand medicineId(Long medicineId) {
        this.setMedicineId(medicineId);
        return this;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return this.medicineName;
    }

    public MedicineSoldPerDemand medicineName(String medicineName) {
        this.setMedicineName(medicineName);
        return this;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Double getMedicineOrderQuantity() {
        return this.medicineOrderQuantity;
    }

    public MedicineSoldPerDemand medicineOrderQuantity(Double medicineOrderQuantity) {
        this.setMedicineOrderQuantity(medicineOrderQuantity);
        return this;
    }

    public void setMedicineOrderQuantity(Double medicineOrderQuantity) {
        this.medicineOrderQuantity = medicineOrderQuantity;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public MedicineSoldPerDemand unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscountedPrice() {
        return this.discountedPrice;
    }

    public MedicineSoldPerDemand discountedPrice(Double discountedPrice) {
        this.setDiscountedPrice(discountedPrice);
        return this;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Integer getIssuedQuantity() {
        return this.issuedQuantity;
    }

    public MedicineSoldPerDemand issuedQuantity(Integer issuedQuantity) {
        this.setIssuedQuantity(issuedQuantity);
        return this;
    }

    public void setIssuedQuantity(Integer issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public Integer getReturnQuantity() {
        return this.returnQuantity;
    }

    public MedicineSoldPerDemand returnQuantity(Integer returnQuantity) {
        this.setReturnQuantity(returnQuantity);
        return this;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Instant getSellingDate() {
        return this.sellingDate;
    }

    public MedicineSoldPerDemand sellingDate(Instant sellingDate) {
        this.setSellingDate(sellingDate);
        return this;
    }

    public void setSellingDate(Instant sellingDate) {
        this.sellingDate = sellingDate;
    }

    public Demand getDemand() {
        return this.demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public MedicineSoldPerDemand demand(Demand demand) {
        this.setDemand(demand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicineSoldPerDemand)) {
            return false;
        }
        return id != null && id.equals(((MedicineSoldPerDemand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicineSoldPerDemand{" +
            "id=" + getId() +
            ", medicineId=" + getMedicineId() +
            ", medicineName='" + getMedicineName() + "'" +
            ", medicineOrderQuantity=" + getMedicineOrderQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", discountedPrice=" + getDiscountedPrice() +
            ", issuedQuantity=" + getIssuedQuantity() +
            ", returnQuantity=" + getReturnQuantity() +
            ", sellingDate='" + getSellingDate() + "'" +
            "}";
    }
}
