package esquadria;

import esquadria.Beans.Esquadria;
import esquadria.Dao.EsquadriaDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EsquadriaDaoTest {

    private EsquadriaDao dao;
    private Esquadria esquadriaTeste;

    public EsquadriaDaoTest() {
    }

    @Before
    public void setUp() {
        dao = new EsquadriaDao();
        esquadriaTeste = new Esquadria();
        esquadriaTeste.setNomeEsquadria("JUnit Esquadria Teste");
        esquadriaTeste.setDescricao("Descricao JUnit");
        esquadriaTeste.setPreco("100.00");
        esquadriaTeste.setDistribuidorId("1"); // ID mockado
    }

    @After
    public void tearDown() {
        Esquadria eOriginal = dao.getPesquisarNome("JUnit Esquadria Teste");
        if (eOriginal != null && eOriginal.getIdEsquadria() > 0) {
            dao.excluir(eOriginal.getIdEsquadria());
        }

        Esquadria eEditado = dao.getPesquisarNome("JUnit Esquadria Teste Editado");
        if (eEditado != null && eEditado.getIdEsquadria() > 0) {
            dao.excluir(eEditado.getIdEsquadria());
        }
    }

    @Test
    public void testInserir() {
        System.out.println("Testando: inserir esquadria");
        dao.inserir(esquadriaTeste);
        
        Esquadria inserido = dao.getPesquisarNome("JUnit Esquadria Teste");
        assertNotNull("Esquadria não deve ser nula", inserido);
        assertEquals("Nome deve ser igual ao inserido", "JUnit Esquadria Teste", inserido.getNomeEsquadria());
        assertTrue("ID deve ser maior que 0", inserido.getIdEsquadria() > 0);
    }

    @Test
    public void testEditar() {
        System.out.println("Testando: editar esquadria");
        dao.inserir(esquadriaTeste);
        
        Esquadria inserido = dao.getPesquisarNome("JUnit Esquadria Teste");
        assertNotNull(inserido);
        assertTrue(inserido.getIdEsquadria() > 0);
        
        inserido.setNomeEsquadria("JUnit Esquadria Teste Editado");
        inserido.setPreco("200.00");
        dao.editar(inserido);
        
        Esquadria alterado = dao.getEsquadria(inserido.getIdEsquadria());
        assertNotNull(alterado);
        assertEquals("JUnit Esquadria Teste Editado", alterado.getNomeEsquadria());
        assertEquals("200.00", alterado.getPreco());
    }

    @Test
    public void testExcluir() {
        System.out.println("Testando: excluir esquadria");
        dao.inserir(esquadriaTeste);
        
        Esquadria inserido = dao.getPesquisarNome("JUnit Esquadria Teste");
        assertNotNull(inserido);
        int id = inserido.getIdEsquadria();
        assertTrue(id > 0);
        
        dao.excluir(id);
        
        Esquadria excluido = dao.getEsquadria(id);
        if (excluido != null) {
            assertNull("Nome deve ser nulo se o objeto foi excluido do banco", excluido.getNomeEsquadria());
        } else {
            assertNull("Retorno deve ser nulo", excluido);
        }
    }
}
