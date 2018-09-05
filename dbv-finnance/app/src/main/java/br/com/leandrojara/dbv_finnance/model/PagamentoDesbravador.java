package br.com.leandrojara.dbv_finnance.model;

import java.util.Date;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;

public class PagamentoDesbravador extends EntityBase {

    private Pagamento pagamento;
    private Desbravador desbravador;
    private Double valor;
    private Date dataPagamento;
    private Double valorPago;

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Desbravador getDesbravador() {
        return desbravador;
    }

    public void setDesbravador(Desbravador desbravador) {
        this.desbravador = desbravador;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    @Override
    public String getCollectionName() {
        return "pagamentoDesbravadores";
    }
}
