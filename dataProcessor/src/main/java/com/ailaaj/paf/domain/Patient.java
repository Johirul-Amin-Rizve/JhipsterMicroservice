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
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mr")
    private String mr;

    @Column(name = "mr_plus_name")
    private String mrPlusName;

    @Column(name = "ward")
    private String ward;

    @Column(name = "room")
    private String room;

    @Column(name = "bed")
    private String bed;

    @Column(name = "admission_date")
    private Instant admissionDate;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "medicineSoldPerDemands", "patient" }, allowSetters = true)
    private Set<Demand> demands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Patient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMr() {
        return this.mr;
    }

    public Patient mr(String mr) {
        this.setMr(mr);
        return this;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getMrPlusName() {
        return this.mrPlusName;
    }

    public Patient mrPlusName(String mrPlusName) {
        this.setMrPlusName(mrPlusName);
        return this;
    }

    public void setMrPlusName(String mrPlusName) {
        this.mrPlusName = mrPlusName;
    }

    public String getWard() {
        return this.ward;
    }

    public Patient ward(String ward) {
        this.setWard(ward);
        return this;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getRoom() {
        return this.room;
    }

    public Patient room(String room) {
        this.setRoom(room);
        return this;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBed() {
        return this.bed;
    }

    public Patient bed(String bed) {
        this.setBed(bed);
        return this;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public Instant getAdmissionDate() {
        return this.admissionDate;
    }

    public Patient admissionDate(Instant admissionDate) {
        this.setAdmissionDate(admissionDate);
        return this;
    }

    public void setAdmissionDate(Instant admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Set<Demand> getDemands() {
        return this.demands;
    }

    public void setDemands(Set<Demand> demands) {
        if (this.demands != null) {
            this.demands.forEach(i -> i.setPatient(null));
        }
        if (demands != null) {
            demands.forEach(i -> i.setPatient(this));
        }
        this.demands = demands;
    }

    public Patient demands(Set<Demand> demands) {
        this.setDemands(demands);
        return this;
    }

    public Patient addDemand(Demand demand) {
        this.demands.add(demand);
        demand.setPatient(this);
        return this;
    }

    public Patient removeDemand(Demand demand) {
        this.demands.remove(demand);
        demand.setPatient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", mr='" + getMr() + "'" +
            ", mrPlusName='" + getMrPlusName() + "'" +
            ", ward='" + getWard() + "'" +
            ", room='" + getRoom() + "'" +
            ", bed='" + getBed() + "'" +
            ", admissionDate='" + getAdmissionDate() + "'" +
            "}";
    }
}
