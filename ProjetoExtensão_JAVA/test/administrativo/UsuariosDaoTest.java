package administrativo;

import administrativo.Beans.Usuario;
import administrativo.Dao.UsuariosDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuariosDaoTest {

    private UsuariosDao dao;
    private Usuario usuarioTeste;

    public UsuariosDaoTest() {
    }

    @Before
    public void setUp() {
        dao = new UsuariosDao();
        usuarioTeste = new Usuario();
        usuarioTeste.setLogin("JUnitLogin");
        usuarioTeste.setSenha("12345");
        usuarioTeste.setTipo("Admin");
    }

    @After
    public void tearDown() {
        int idOriginal = findIdByLogin("JUnitLogin");
        if (idOriginal > 0) {
            dao.excluir(idOriginal);
        }

        int idEditado = findIdByLogin("JUnitLoginEditado");
        if (idEditado > 0) {
            dao.excluir(idEditado);
        }
    }

    private int findIdByLogin(String login) {
        for (Usuario u : dao.getUsuario()) {
            if (login.equals(u.getLogin())) {
                return u.getIdUsuario();
            }
        }
        return -1;
    }

    @Test
    public void testInserir() {
        System.out.println("Testando: inserir usuario");
        dao.inserir(usuarioTeste);
        
        int id = findIdByLogin("JUnitLogin");
        assertTrue("Usuario deve ter sido inserido e retornado na lista com ID > 0", id > 0);
        
        Usuario inserido = dao.getUsuario(id);
        assertNotNull(inserido);
        assertEquals("JUnitLogin", inserido.getLogin());
    }

    @Test
    public void testEditar() {
        System.out.println("Testando: editar usuario");
        dao.inserir(usuarioTeste);
        
        int id = findIdByLogin("JUnitLogin");
        assertTrue(id > 0);
        
        Usuario inserido = dao.getUsuario(id);
        inserido.setLogin("JUnitLoginEditado");
        inserido.setTipo("User");
        dao.editar(inserido);
        
        Usuario alterado = dao.getUsuario(id);
        assertNotNull(alterado);
        assertEquals("JUnitLoginEditado", alterado.getLogin());
        assertEquals("User", alterado.getTipo());
    }

    @Test
    public void testExcluir() {
        System.out.println("Testando: excluir usuario");
        dao.inserir(usuarioTeste);
        
        int id = findIdByLogin("JUnitLogin");
        assertTrue(id > 0);
        
        dao.excluir(id);
        
        Usuario excluido = dao.getUsuario(id);
        if (excluido != null) {
            assertNull("Login deve ser nulo se foi excluido", excluido.getLogin());
        } else {
            assertNull(excluido);
        }
    }
}
