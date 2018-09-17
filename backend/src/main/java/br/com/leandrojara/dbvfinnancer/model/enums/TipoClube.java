package br.com.leandrojara.dbvfinnancer.model.enums;

public enum TipoClube {

    DESBRAVADORES("Clube de Desbravadores"),
    AVENTUREIROS("Clube de Aventureiros");

    private final String descricao;

    TipoClube(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
