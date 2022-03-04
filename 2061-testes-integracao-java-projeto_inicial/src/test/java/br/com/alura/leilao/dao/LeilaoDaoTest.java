package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
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
        Usuario usuario = criarUsuario();
        Leilao leilao = criarLeilao(usuario);
        Leilao salvo = leilaoDao.buscarPorId(leilao.getId());
        assertEquals(leilao.getNome(),salvo.getNome());
        assertEquals(leilao.getDataAbertura(),salvo.getDataAbertura());
        assertEquals(leilao.getValorInicial(),salvo.getValorInicial());

    }

    @Test
    void atualizarLeilaoExistente(){
        Usuario usuario = criarUsuario();
        Leilao leilao = criarLeilao(usuario);
        leilao.setNome("novo nome do leilão");
        leilao = leilaoDao.salvar(leilao);
        Leilao salvo = leilaoDao.buscarPorId(leilao.getId());
        assertEquals(leilao.getNome(),salvo.getNome());


    }

    private Leilao criarLeilao(Usuario usuario) {
        Leilao leilao = new Leilao("leilão Teste", new BigDecimal("1234"), LocalDate.now(), usuario);
        leilao = leilaoDao.salvar(leilao);
        return leilao;
    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "senha123");
        entityManager.persist(usuario);
        return usuario;

    }

}
