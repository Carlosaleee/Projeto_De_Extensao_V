package administrativo;

import administrativo.Beans.Funcionario;
import administrativo.Dao.FuncionariosDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class FuncionariosDaoTest {

    private FuncionariosDao dao;
    private Funcionario funcionarioTeste;

    public FuncionariosDaoTest() {
    }

    @Before
    public void setUp() {
        dao = new FuncionariosDao();
        funcionarioTeste = new Funcionario();
        funcionarioTeste.setNome("JUnit Funcionario Teste");
        funcionarioTeste.setTelefone01("11999999999");
        funcionarioTeste.setTelefone02("11888888888");
        funcionarioTeste.setDataAdmissao("01/01/2023");
        funcionarioTeste.setDataDemissao("");
        funcionarioTeste.setNumcarteiraTrabalho("1234567");
        funcionarioTeste.setRg("112223334");
        funcionarioTeste.setRua("Rua JUnit");
        funcionarioTeste.setNumero("123");
        funcionarioTeste.setBalneario("Bairro JUnit");
        funcionarioTeste.setCidade("Cidade JUnit");
        funcionarioTeste.setCargoId("1"); // Assumindo que pode ser string/int na base
        funcionarioTeste.setUsuarioId("1"); // Assumindo que pode ser string/int na base
    }

    @After
    public void tearDown() {
        Funcionario fOriginal = dao.getFuncionarioNome("JUnit Funcionario Teste");
        if (fOriginal != null && fOriginal.getIdFuncionario() > 0) {
            dao.excluir(fOriginal.getIdFuncionario());
        }

        Funcionario fEditado = dao.getFuncionarioNome("JUnit Funcionario Teste Editado");
        if (fEditado != null && fEditado.getIdFuncionario() > 0) {
            dao.excluir(fEditado.getIdFuncionario());
        }
    }

    @Test
    public void testInserir() {
        System.out.println("Testando: inserir funcionario");
        dao.inserir(funcionarioTeste);
        
        Funcionario inserido = dao.getFuncionarioNome("JUnit Funcionario Teste");
        assertNotNull(inserido);
        assertEquals("JUnit Funcionario Teste", inserido.getNome());
        assertTrue(inserido.getIdFuncionario() > 0);
    }

    @Test
    public void testEditar() {
        System.out.println("Testando: editar funcionario");
        dao.inserir(funcionarioTeste);
        
        Funcionario inserido = dao.getFuncionarioNome("JUnit Funcionario Teste");
        assertNotNull(inserido);
        assertTrue(inserido.getIdFuncionario() > 0);
        
        inserido.setNome("JUnit Funcionario Teste Editado");
        inserido.setCidade("Nova Cidade JUnit");
        dao.editar(inserido);
        
        Funcionario alterado = dao.getFuncionario(inserido.getIdFuncionario());
        assertNotNull(alterado);
        assertEquals("JUnit Funcionario Teste Editado", alterado.getNome());
        assertEquals("Nova Cidade JUnit", alterado.getCidade());
    }

    @Test
    public void testExcluir() {
        System.out.println("Testando: excluir funcionario");
        dao.inserir(funcionarioTeste);
        
        Funcionario inserido = dao.getFuncionarioNome("JUnit Funcionario Teste");
        assertNotNull(inserido);
        int id = inserido.getIdFuncionario();
        assertTrue(id > 0);
        
        dao.excluir(id);
        
        Funcionario excluido = dao.getFuncionario(id);
        if (excluido != null) {
            assertNull("Nome deve ser nulo se foi excluido", excluido.getNome());
        } else {
            assertNull(excluido);
        }
    }
}
