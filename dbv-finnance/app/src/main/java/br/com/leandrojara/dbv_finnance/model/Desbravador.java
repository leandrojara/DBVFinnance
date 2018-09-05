package br.com.leandrojara.dbv_finnance.model;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;

public class Desbravador extends EntityBase {

    private String nome;
    private Clube clube;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Clube getClube() {
        return clube;
    }

    public void setClube(Clube clube) {
        this.clube = clube;
    }

    @Override
    public String getCollectionName() {
        return "desbravadores";
    }
}
