package br.com.leandrojara.dbv_finnance.model;

import java.util.Date;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;

public class Pagamento extends EntityBase {

    private String descricao;
    private Date dataInicial;
    private Date dataFinal;
    private Clube clube;
    private Double valor;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Clube getClube() {
        return clube;
    }

    public void setClube(Clube clube) {
        this.clube = clube;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String getCollectionName() {
        return "pagamentos";
    }
}
