package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.LeilaoBuilder;
import br.com.alura.leilao.util.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LeilaoDaoTest {


    private LeilaoDao leilaoDao;
    private EntityManager entityManager;

    @BeforeEach
    void iniciarEntityManager(){
        entityManager =  JPAUtil.getEntityManager();
        leilaoDao = new LeilaoDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void commitTransaction(){
        entityManager.getTransaction().rollback();
    }

    @Test
    void salvarNovoLeilao(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@email.com")
                .comSenha("senha123")
                .build();

        entityManager.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("leilão Teste")
                .comValorInicial(new BigDecimal("1234"))
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .build();
        leilao = leilaoDao.salvar(leilao);

        Leilao salvo = leilaoDao.buscarPorId(leilao.getId());
        assertEquals(leilao.getNome(),salvo.getNome());
        assertEquals(leilao.getDataAbertura(),salvo.getDataAbertura());
        assertEquals(leilao.getValorInicial(),salvo.getValorInicial());

    }

    @Test
    void atualizarLeilaoExistente(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@email.com")
                .comSenha("senha123")
                .build();

        entityManager.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("leilão Teste")
                .comValorInicial(new BigDecimal("1234"))
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .build();
        leilao = leilaoDao.salvar(leilao);

        leilao.setNome("novo nome do leilão");
        leilao = leilaoDao.salvar(leilao);
        Leilao salvo = leilaoDao.buscarPorId(leilao.getId());
        assertEquals(leilao.getNome(),salvo.getNome());


    }

    
}
