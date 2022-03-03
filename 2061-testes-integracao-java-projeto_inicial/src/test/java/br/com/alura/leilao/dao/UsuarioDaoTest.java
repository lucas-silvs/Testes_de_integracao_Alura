package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
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
    void BuscarUsuarioPorUserNameQueExisteEDeveRetornarOUsuario() {



        Usuario usuario = criarUsuario();


        Usuario response = usuarioDao.buscarPorUsername(usuario.getNome());
        assertNotNull(response);
    }

    @Test
    void BuscarUsuarioPorUserNameQueNaoExisteEDeveRetornarNull() {

        assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername("fulano"));
    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "senha123");
        entityManager.persist(usuario);
        return usuario;

    }

}