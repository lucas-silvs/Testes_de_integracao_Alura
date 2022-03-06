package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {

    private UsuarioDao usuarioDao;
    private EntityManager entityManager;

    @BeforeEach
    void iniciarEntityManager(){
        entityManager =  JPAUtil.getEntityManager();
        usuarioDao = new UsuarioDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void commitTransaction(){
        entityManager.getTransaction().rollback();
    }


    @Test
    void buscarUsuarioPorUserNameQueExisteEDeveRetornarOUsuario() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@email.com")
                .comSenha("senha123")
                .build();

        entityManager.persist(usuario);
        Usuario response = usuarioDao.buscarPorUsername(usuario.getNome());
        assertNotNull(response);
    }

    @Test
    void buscarUsuarioPorUserNameQueNaoExisteEDeveRetornarNull() {

        assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername("fulano"));
    }

    @Test
    void deletarUsuarioQueExisteNaBase() {

        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@email.com")
                .comSenha("senha123")
                .build();

        entityManager.persist(usuario);
        usuarioDao.deletar(usuario);
        assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername(usuario.getNome()));
    }


}