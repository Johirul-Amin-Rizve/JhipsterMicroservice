package com.ailaaj.paf.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PafAilaajMedicineSkuMap.
 */
@Entity
@Table(name = "paf_ailaaj_medicine_sku_map")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PafAilaajMedicineSkuMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ailaaj_medicine_id")
    private Long ailaajMedicineId;

    @Column(name = "paf_medicine_id")
    private Long pafMedicineId;

    @Column(name = "paf_medicine_name")
    private String pafMedicineName;

    @Column(name = "medicine_name")
    private String medicineName;

    @Column(name = "c_factorbrand")
    private Long cFactorbrand;

    @Column(name = "supplier")
    private String supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PafAilaajMedicineSkuMap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAilaajMedicineId() {
        return this.ailaajMedicineId;
    }

    public PafAilaajMedicineSkuMap ailaajMedicineId(Long ailaajMedicineId) {
        this.setAilaajMedicineId(ailaajMedicineId);
        return this;
    }

    public void setAilaajMedicineId(Long ailaajMedicineId) {
        this.ailaajMedicineId = ailaajMedicineId;
    }

    public Long getPafMedicineId() {
        return this.pafMedicineId;
    }

    public PafAilaajMedicineSkuMap pafMedicineId(Long pafMedicineId) {
        this.setPafMedicineId(pafMedicineId);
        return this;
    }

    public void setPafMedicineId(Long pafMedicineId) {
        this.pafMedicineId = pafMedicineId;
    }

    public String getPafMedicineName() {
        return this.pafMedicineName;
    }

    public PafAilaajMedicineSkuMap pafMedicineName(String pafMedicineName) {
        this.setPafMedicineName(pafMedicineName);
        return this;
    }

    public void setPafMedicineName(String pafMedicineName) {
        this.pafMedicineName = pafMedicineName;
    }

    public String getMedicineName() {
        return this.medicineName;
    }

    public PafAilaajMedicineSkuMap medicineName(String medicineName) {
        this.setMedicineName(medicineName);
        return this;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Long getcFactorbrand() {
        return this.cFactorbrand;
    }

    public PafAilaajMedicineSkuMap cFactorbrand(Long cFactorbrand) {
        this.setcFactorbrand(cFactorbrand);
        return this;
    }

    public void setcFactorbrand(Long cFactorbrand) {
        this.cFactorbrand = cFactorbrand;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public PafAilaajMedicineSkuMap supplier(String supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PafAilaajMedicineSkuMap)) {
            return false;
        }
        return id != null && id.equals(((PafAilaajMedicineSkuMap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PafAilaajMedicineSkuMap{" +
            "id=" + getId() +
            ", ailaajMedicineId=" + getAilaajMedicineId() +
            ", pafMedicineId=" + getPafMedicineId() +
            ", pafMedicineName='" + getPafMedicineName() + "'" +
            ", medicineName='" + getMedicineName() + "'" +
            ", cFactorbrand=" + getcFactorbrand() +
            ", supplier='" + getSupplier() + "'" +
            "}";
    }
}
