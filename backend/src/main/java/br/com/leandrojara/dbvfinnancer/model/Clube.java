package br.com.leandrojara.dbvfinnancer.model;

import br.com.leandrojara.dbvfinnancer.model.enums.TipoClube;

import javax.persistence.*;

@Entity
@Table(name = "clube")
public class Clube {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoClube tipo;

    @JoinColumn(name = "id_diretor", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario diretor;

    @JoinColumn(name = "id_tesoureiro", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario tesoureiro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
