package administrativo;

import administrativo.Beans.Cargo;
import administrativo.Dao.CargosDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CargosDaoTest {

    private CargosDao dao;
    private Cargo cargoTeste;

    public CargosDaoTest() {
    }

    @Before
    public void setUp() {
        dao = new CargosDao();
        cargoTeste = new Cargo();
        cargoTeste.setNomeCargo("JUnit Cargo Teste");
        cargoTeste.setDescricao("Descricao JUnit");
        cargoTeste.setSalario("5000.00");
    }

    @After
    public void tearDown() {
        Cargo cOriginal = dao.getNomeCargo("JUnit Cargo Teste");
        if (cOriginal != null && cOriginal.getIdCargo() > 0) {
            dao.excluir(cOriginal.getIdCargo());
        }

        Cargo cEditado = dao.getNomeCargo("JUnit Cargo Teste Editado");
        if (cEditado != null && cEditado.getIdCargo() > 0) {
            dao.excluir(cEditado.getIdCargo());
        }
    }

    @Test
    public void testInserir() {
        System.out.println("Testando: inserir cargo");
        dao.inserir(cargoTeste);
        
        Cargo inserido = dao.getNomeCargo("JUnit Cargo Teste");
        assertNotNull("O cargo retornado não deve ser nulo.", inserido);
        assertEquals("O nome do cargo deve coincidir.", "JUnit Cargo Teste", inserido.getNomeCargo());
    }

    @Test
    public void testEditar() {
        System.out.println("Testando: editar cargo");
        dao.inserir(cargoTeste);
        
        Cargo inserido = dao.getNomeCargo("JUnit Cargo Teste");
        assertNotNull(inserido);
        assertTrue(inserido.getIdCargo() > 0);
        
        inserido.setNomeCargo("JUnit Cargo Teste Editado");
        inserido.setSalario("6000.00");
        dao.editar(inserido);
        
        Cargo alterado = dao.getCargo(inserido.getIdCargo());
        assertNotNull(alterado);
        assertEquals("JUnit Cargo Teste Editado", alterado.getNomeCargo());
        assertEquals("6000.00", alterado.getSalario());
    }

    @Test
    public void testExcluir() {
        System.out.println("Testando: excluir cargo");
        dao.inserir(cargoTeste);
        
        Cargo inserido = dao.getNomeCargo("JUnit Cargo Teste");
        assertNotNull(inserido);
        int id = inserido.getIdCargo();
        assertTrue(id > 0);
        
        dao.excluir(id);
        
        Cargo excluido = dao.getCargo(id);
        if (excluido != null) {
            assertNull("O nome do cargo deve ser nulo se foi excluido", excluido.getNomeCargo());
        } else {
            assertNull(excluido);
        }
    }
}
