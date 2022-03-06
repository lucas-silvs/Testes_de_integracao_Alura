package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.LanceBuilder;
import br.com.alura.leilao.util.LeilaoBuilder;
import br.com.alura.leilao.util.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

 class LanceDaoTest {


    private LanceDao lanceDao;
    private EntityManager entityManager;

    @BeforeEach
    void iniciarEntityManager(){
        entityManager =  JPAUtil.getEntityManager();
        lanceDao = new LanceDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void commitTransaction(){
        entityManager.getTransaction().rollback();
    }

    @Test
    void deveRetornarMaiorLanceDoLeilao(){

        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@email.com")
                .comSenha("senha123")
                .build();

        entityManager.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("leilão Teste")
                .comValorInicial(new BigDecimal("100"))
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .build();
        entityManager.persist(leilao);

        Lance lance = new LanceBuilder()
                .comValor(new BigDecimal("1000"))
                .comUsuario(usuario)
                .comLeilao(leilao)
                .build();

        leilao.propoe(lance);
        entityManager.persist(lance);

        Usuario usuario2 = new UsuarioBuilder()
                .comNome("ciclano")
                .comEmail("ciclano@email.com")
                .comSenha("senha123")
                .build();

        entityManager.persist(usuario);


        Lance lance2 = new LanceBuilder()
                .comValor(new BigDecimal("2000"))
                .comUsuario(usuario)
                .comLeilao(leilao)
                .build();

        leilao.propoe(lance2);
        entityManager.persist(lance2);

        Lance maiorLance = lanceDao.buscarMaiorLanceDoLeilao(leilao);

        Assertions.assertEquals(new BigDecimal("2000"),maiorLance.getValor());



    }

}
