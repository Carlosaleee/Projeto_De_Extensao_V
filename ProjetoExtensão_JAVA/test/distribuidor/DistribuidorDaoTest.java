package distribuidor;

import distribuidor.Beans.Distribuidor;
import distribuidor.Dao.DistribuidorDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes automatizados com JUnit 4 para DistribuidorDao.
 */
public class DistribuidorDaoTest {

    private DistribuidorDao dao;
    private Distribuidor distribuidorTeste;

    public DistribuidorDaoTest() {
    }

    /**
     * Executado antes de CADA método @Test.
     * Prepara os dados de teste.
     */
    @Before
    public void setUp() {
        dao = new DistribuidorDao();
        distribuidorTeste = new Distribuidor();
        distribuidorTeste.setNomeEmpresa("JUnit Distribuidor Teste");
        distribuidorTeste.setCnpj("11111111111111");
        distribuidorTeste.setTelefone01("11999999999");
        distribuidorTeste.setTelefone02("11888888888");
        distribuidorTeste.setResponsavel("Resp JUnit");
        distribuidorTeste.setSetor("Setor JUnit");
        distribuidorTeste.setCidade("Cidade JUnit");
    }

    /**
     * Executado após CADA método @Test.
     * Garante a limpeza do banco excluindo os dados que o teste acabou de criar.
     */
    @After
    public void tearDown() {
        // Tenta achar pelo nome original e exclui
        Distribuidor dOriginal = dao.getNomeEmpresa("JUnit Distribuidor Teste");
        if (dOriginal != null && dOriginal.getIdDistribuidor() > 0) {
            dao.excluir(dOriginal.getIdDistribuidor());
        }

        // Tenta achar pelo nome editado e exclui (usado no testEditar)
        Distribuidor dEditado = dao.getNomeEmpresa("JUnit Distribuidor Teste Editado");
        if (dEditado != null && dEditado.getIdDistribuidor() > 0) {
            dao.excluir(dEditado.getIdDistribuidor());
        }
    }

    @Test
    public void testInserir() {
        System.out.println("Testando: inserir distribuidor");

        // Executa a inserção
        dao.inserir(distribuidorTeste);

        // Verifica buscando pelo nome da empresa
        Distribuidor inserido = dao.getNomeEmpresa("JUnit Distribuidor Teste");

        assertNotNull("O distribuidor retornado não deve ser nulo.", inserido);
        assertEquals("O nome da empresa deve coincidir com o que foi inserido.", "JUnit Distribuidor Teste",
                inserido.getNomeEmpresa());
        assertTrue("O ID gerado deve ser maior que 0.", inserido.getIdDistribuidor() > 0);
    }

    @Test
    public void testEditar() {
        System.out.println("Testando: editar distribuidor");

        // 1. Insere o distribuidor
        dao.inserir(distribuidorTeste);

        // 2. Busca para recuperar o ID
        Distribuidor inserido = dao.getNomeEmpresa("JUnit Distribuidor Teste");
        assertNotNull("Não encontrou o distribuidor recém inserido", inserido);
        assertTrue(inserido.getIdDistribuidor() > 0);

        // 3. Modifica os dados
        inserido.setNomeEmpresa("JUnit Distribuidor Teste Editado");
        inserido.setCidade("Nova Cidade JUnit");

        // 4. Executa a edição
        dao.editar(inserido);

        // 5. Busca pelo ID para validar as alterações
        Distribuidor alterado = dao.getDistribuidor(inserido.getIdDistribuidor());

        assertNotNull("O distribuidor alterado não pode ser nulo", alterado);
        assertEquals("JUnit Distribuidor Teste Editado", alterado.getNomeEmpresa());
        assertEquals("Nova Cidade JUnit", alterado.getCidade());
    }

    @Test
    public void testExcluir() {
        System.out.println("Testando: excluir distribuidor");

        // 1. Insere o distribuidor
        dao.inserir(distribuidorTeste);

        // 2. Busca para recuperar o ID
        Distribuidor inserido = dao.getNomeEmpresa("JUnit Distribuidor Teste");
        assertNotNull(inserido);
        int idParaExcluir = inserido.getIdDistribuidor();
        assertTrue(idParaExcluir > 0);

        // 3. Executa a exclusão
        dao.excluir(idParaExcluir);

        // 4. Tenta buscar pelo ID, deve retornar nulo ou um objeto vazio
        Distribuidor excluido = dao.getDistribuidor(idParaExcluir);

        if (excluido != null) {
            assertNull("O nome deve ser nulo se não achou registro no banco", excluido.getNomeEmpresa());
        } else {
            assertNull("O retorno deve ser nulo", excluido);
        }
    }
}
