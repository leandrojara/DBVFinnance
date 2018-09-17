package br.com.leandrojara.dbvfinnancer.model;

import javax.persistence.*;

@Entity
@Table(name = "desbravador")
public class Desbravador {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JoinColumn(name = "id_clube", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Clube clube;

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

    public Clube getClube() {
        return clube;
    }

    public void setClube(Clube clube) {
        this.clube = clube;
    }
}
