package br.com.leandrojara.dbvfinnancer.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pagamento_desbravador")
public class PagamentoDesbravador {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "id_pagamento", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pagamento pagamento;

    @JoinColumn(name = "id_desbravador", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Desbravador desbravador;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Column(name = "valor_pago")
    private Double valorPago;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
