package br.com.leandrojara.dbv_finnance.model;

import java.util.List;
import java.util.Map;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;
import br.com.leandrojara.dbv_finnance.model.enums.Role;

public class Usuario extends EntityBase {

    private String nome;
    private String email;
    private List<Role> roles;
    private List<Desbravador> desbravadores;

    public Usuario() {
        super();
    }

    public Usuario(Map<String, Object> data) {
        super(data);
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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