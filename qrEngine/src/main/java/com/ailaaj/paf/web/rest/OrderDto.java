package com.ailaaj.paf.web.rest;

public class OrderDto {
    private long ailaajMedicineId;
    private long medicineId;

    public long getAilaajMedicineId() {
        return ailaajMedicineId;
    }

    public void setAilaajMedicineId(long ailaajMedicineId) {
        this.ailaajMedicineId = ailaajMedicineId;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }
}
