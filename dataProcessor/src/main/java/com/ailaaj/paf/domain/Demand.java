package com.ailaaj.paf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Demand.
 */
@Entity
@Table(name = "demand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Demand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "invoice_status")
    private String invoiceStatus;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(mappedBy = "demand")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demand" }, allowSetters = true)
    private Set<MedicineSoldPerDemand> medicineSoldPerDemands = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "demands" }, allowSetters = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public Demand orderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public Demand orderStatus(String orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getInvoiceStatus() {
        return this.invoiceStatus;
    }

    public Demand invoiceStatus(String invoiceStatus) {
        this.setInvoiceStatus(invoiceStatus);
        return this;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public Demand hospitalName(String hospitalName) {
        this.setHospitalName(hospitalName);
        return this;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Demand createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<MedicineSoldPerDemand> getMedicineSoldPerDemands() {
        return this.medicineSoldPerDemands;
    }

    public void setMedicineSoldPerDemands(Set<MedicineSoldPerDemand> medicineSoldPerDemands) {
        if (this.medicineSoldPerDemands != null) {
            this.medicineSoldPerDemands.forEach(i -> i.setDemand(null));
        }
        if (medicineSoldPerDemands != null) {
            medicineSoldPerDemands.forEach(i -> i.setDemand(this));
        }
        this.medicineSoldPerDemands = medicineSoldPerDemands;
    }

    public Demand medicineSoldPerDemands(Set<MedicineSoldPerDemand> medicineSoldPerDemands) {
        this.setMedicineSoldPerDemands(medicineSoldPerDemands);
        return this;
    }

    public Demand addMedicineSoldPerDemand(MedicineSoldPerDemand medicineSoldPerDemand) {
        this.medicineSoldPerDemands.add(medicineSoldPerDemand);
        medicineSoldPerDemand.setDemand(this);
        return this;
    }

    public Demand removeMedicineSoldPerDemand(MedicineSoldPerDemand medicineSoldPerDemand) {
        this.medicineSoldPerDemands.remove(medicineSoldPerDemand);
        medicineSoldPerDemand.setDemand(null);
        return this;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Demand patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demand)) {
            return false;
        }
        return id != null && id.equals(((Demand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demand{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", invoiceStatus='" + getInvoiceStatus() + "'" +
            ", hospitalName='" + getHospitalName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
