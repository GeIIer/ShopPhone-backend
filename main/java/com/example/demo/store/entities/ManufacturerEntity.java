package com.example.demo.store.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manufacturer")
public class ManufacturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idManuFact;
    private String nameManuFact;

    public Long getIdManuFact() {
        return idManuFact;
    }

    public void setIdManuFact(Long idManuFact) {
        this.idManuFact = idManuFact;
    }

    public String getNameManuFact() {
        return nameManuFact;
    }

    public void setNameManuFact(String nameManuFact) {
        this.nameManuFact = nameManuFact;
    }
}
