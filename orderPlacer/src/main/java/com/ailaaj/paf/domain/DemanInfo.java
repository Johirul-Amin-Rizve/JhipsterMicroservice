package com.ailaaj.paf.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemanInfo.
 */
@Entity
@Table(name = "deman_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemanInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "demand_id")
    private String demandId;

    @Column(name = "demand_status")
    private String demandStatus;

    @Column(name = "invoice_status")
    private String invoiceStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemanInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandId() {
        return this.demandId;
    }

    public DemanInfo demandId(String demandId) {
        this.setDemandId(demandId);
        return this;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getDemandStatus() {
        return this.demandStatus;
    }

    public DemanInfo demandStatus(String demandStatus) {
        this.setDemandStatus(demandStatus);
        return this;
    }

    public void setDemandStatus(String demandStatus) {
        this.demandStatus = demandStatus;
    }

    public String getInvoiceStatus() {
        return this.invoiceStatus;
    }

    public DemanInfo invoiceStatus(String invoiceStatus) {
        this.setInvoiceStatus(invoiceStatus);
        return this;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemanInfo)) {
            return false;
        }
        return id != null && id.equals(((DemanInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemanInfo{" +
            "id=" + getId() +
            ", demandId='" + getDemandId() + "'" +
            ", demandStatus='" + getDemandStatus() + "'" +
            ", invoiceStatus='" + getInvoiceStatus() + "'" +
            "}";
    }
}
