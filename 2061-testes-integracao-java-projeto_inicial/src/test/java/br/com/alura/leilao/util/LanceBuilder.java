package br.com.alura.leilao.util;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LanceBuilder {

    private BigDecimal valor;
    private Usuario usuario;
    private Leilao leilao;


    public LanceBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }
    public LanceBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }
    public LanceBuilder comLeilao(Leilao leilao) {
        this.leilao = leilao;
        return this;
    }

    public Lance build() {
        Lance lance =new Lance(usuario,valor);
        lance.setLeilao(leilao);
        return lance;
    }
}
