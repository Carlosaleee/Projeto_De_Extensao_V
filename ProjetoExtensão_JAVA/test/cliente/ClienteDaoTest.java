package cliente;

import cliente.Beans.Cliente;
import cliente.Dao.ClienteDao;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes automatizados com JUnit para o ClienteDao.
 */
public class ClienteDaoTest {

    private ClienteDao dao;
    private Cliente clienteTeste;

    public ClienteDaoTest() {
    }

    /**
     * Executado antes de CADA método @Test.
     * Prepara um objeto Cliente fictício e inicializa o DAO.
     */
    @Before
    public void setUp() {
        dao = new ClienteDao();
        clienteTeste = new Cliente();
        clienteTeste.setNome("JUnit Teste Cliente");
        clienteTeste.setCpf("00000000000");
        clienteTeste.setTelefone01("11999999999");
        clienteTeste.setTelefone02("11888888888");
        clienteTeste.setRua("Rua Teste JUnit");
        clienteTeste.setNumero("S/N");
        clienteTeste.setBalneario("Balneario Teste");
        clienteTeste.setCidade("Cidade Teste");
        clienteTeste.setEstado("TS");
    }

    /**
     * Executado após CADA método @Test.
     * Limpa a sujeira do banco de dados (exclui o que o teste inseriu).
     */
    @After
    public void tearDown() {
        // Busca todos os clientes de teste que sobraram no banco e exclui
        List<Cliente> lista = dao.getCliente("JUnit Teste Cliente");
        if (lista != null) {
            for (Cliente c : lista) {
                dao.excluir(c.getIdCliente());
            }
        }
    }

    @Test
    public void testInserir() {
        System.out.println("Testando: inserir");
        
        // Executa a inserção
        dao.inserir(clienteTeste);
        
        // Verifica se o cliente realmente foi para o banco buscando pelo nome
        List<Cliente> lista = dao.getCliente("JUnit Teste Cliente");
        
        assertNotNull("A lista retornada não deve ser nula.", lista);
        assertFalse("A lista deve conter pelo menos 1 cliente após a inserção.", lista.isEmpty());
        
        boolean encontrou = false;
        for (Cliente c : lista) {
            if (c.getNome().equals("JUnit Teste Cliente")) {
                encontrou = true;
                break;
            }
        }
        assertTrue("O cliente inserido com o nome especificado deve existir no banco.", encontrou);
    }

    @Test
    public void testEditar() {
        System.out.println("Testando: editar");
        
        // 1. Insere o cliente para termos um registro válido
        dao.inserir(clienteTeste);
        
        // 2. Busca para recuperar o ID gerado pelo banco
        List<Cliente> lista = dao.getCliente("JUnit Teste Cliente");
        assertFalse("Deveria ter encontrado o cliente que acabou de ser inserido", lista.isEmpty());
        Cliente clienteInserido = lista.get(0);
        
        // 3. Modifica os dados do objeto
        clienteInserido.setNome("JUnit Teste Cliente Editado");
        clienteInserido.setCidade("Nova Cidade Teste");
        
        // 4. Executa a edição
        dao.editar(clienteInserido);
        
        // 5. Busca o cliente atualizado pelo ID para validar
        Cliente clienteAlterado = dao.getCliente(clienteInserido.getIdCliente());
        
        assertNotNull("O cliente não pode ser nulo após a edição.", clienteAlterado);
        assertEquals("O nome do cliente deve estar atualizado.", "JUnit Teste Cliente Editado", clienteAlterado.getNome());
        assertEquals("A cidade do cliente deve estar atualizada.", "Nova Cidade Teste", clienteAlterado.getCidade());
        
        // Limpeza manual para esse teste, pois mudamos o nome que seria buscado no tearDown
        dao.excluir(clienteAlterado.getIdCliente());
    }

    @Test
    public void testExcluir() {
        System.out.println("Testando: excluir");
        
        // 1. Insere o cliente para termos o que excluir
        dao.inserir(clienteTeste);
        
        // 2. Busca o cliente para recuperar o ID
        List<Cliente> lista = dao.getCliente("JUnit Teste Cliente");
        assertFalse("Deveria ter encontrado o cliente para exclusão", lista.isEmpty());
        int idParaExcluir = lista.get(0).getIdCliente();
        
        // 3. Executa a exclusão
        dao.excluir(idParaExcluir);
        
        // 4. Tenta buscar novamente pelo ID, esperando que não encontre mais
        Cliente clienteExcluido = dao.getCliente(idParaExcluir);
        
        // Se a busca falhar por não encontrar registro, a sua DAO atual retorna null (devido à Exception capturada).
        if (clienteExcluido != null) {
             assertNull("O nome do cliente deveria ser nulo se o registro não existe", clienteExcluido.getNome());
        } else {
             assertNull("O retorno deve ser nulo pois o cliente foi excluído", clienteExcluido);
        }
    }
}
