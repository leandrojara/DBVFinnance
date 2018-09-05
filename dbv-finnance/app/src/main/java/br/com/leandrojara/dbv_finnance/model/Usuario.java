package br.com.leandrojara.dbv_finnance.model;

import java.util.List;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;
import br.com.leandrojara.dbv_finnance.model.enums.Role;

public class Usuario extends EntityBase {

    private String nome;
    private String email;
    private Role role;
    private List<Desbravador> desbravadores;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Desbravador> getDesbravadores() {
        return desbravadores;
    }

    public void setDesbravadores(List<Desbravador> desbravadores) {
        this.desbravadores = desbravadores;
    }

    @Override
    public String getCollectionName() {
        return "usuarios";
    }
}