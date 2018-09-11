package br.com.leandrojara.dbv_finnance.model;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;
import br.com.leandrojara.dbv_finnance.model.enums.TipoClube;

public class Clube extends EntityBase {

    private String nome;
    private TipoClube tipo;
    private Usuario diretor;
    private Usuario tesoureiro;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoClube getTipo() {
        return tipo;
    }

    public void setTipo(TipoClube tipo) {
        this.tipo = tipo;
    }

    public Usuario getDiretor() {
        return diretor;
    }

    public void setDiretor(Usuario diretor) {
        this.diretor = diretor;
    }

    public Usuario getTesoureiro() {
        return tesoureiro;
    }

    public void setTesoureiro(Usuario tesoureiro) {
        this.tesoureiro = tesoureiro;
    }

    @Override
    public String getCollectionName() {
        return "clubes";
    }

    @Override
    public String toString() {
        return tipo.getDescricao() + " " + nome;
    }
}
