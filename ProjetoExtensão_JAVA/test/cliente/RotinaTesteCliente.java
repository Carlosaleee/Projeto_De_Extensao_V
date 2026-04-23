package cliente;

import cliente.Beans.Cliente;
import cliente.Dao.ClienteDao;
import java.util.List;

public class RotinaTesteCliente {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Rotina de Testes para Cliente ===");

        ClienteDao dao = new ClienteDao();

        // 1. Teste de Inserção (Create)
        System.out.println("\n1. Testando Inserção...");
        Cliente novoCliente = new Cliente();
        novoCliente.setNome("Cliente Teste Silva");
        novoCliente.setCpf("12345678900");
        novoCliente.setTelefone01("11999999999");
        novoCliente.setTelefone02("11888888888");
        novoCliente.setRua("Rua dos Testes");
        novoCliente.setNumero("123");
        novoCliente.setBalneario("Centro");
        novoCliente.setCidade("São Paulo");
        novoCliente.setEstado("SP");

        dao.inserir(novoCliente);
        System.out.println("Cliente inserido com sucesso (verifique no banco se foi salvo).");

        // 2. Teste de Busca por Nome (Read - list)
        System.out.println("\n2. Testando Busca por Nome...");
        List<Cliente> clientesEncontrados = dao.getCliente("Cliente Teste");
        
        int idParaEditar = -1;

        if (clientesEncontrados != null && !clientesEncontrados.isEmpty()) {
            System.out.println("Clientes encontrados: " + clientesEncontrados.size());
            for (Cliente c : clientesEncontrados) {
                System.out.println("ID: " + c.getIdCliente() + " | Nome: " + c.getNome() + " | CPF: " + c.getCpf());
                idParaEditar = c.getIdCliente(); // Pega o último ID para o teste de edição
            }
        } else {
            System.out.println("Nenhum cliente encontrado com esse nome.");
        }

        // 3. Teste de Edição (Update)
        if (idParaEditar != -1) {
            System.out.println("\n3. Testando Edição...");
            Cliente clienteParaEditar = dao.getCliente(idParaEditar);
            
            if (clienteParaEditar != null) {
                System.out.println("Nome antigo: " + clienteParaEditar.getNome());
                clienteParaEditar.setNome("Cliente Teste Alterado");
                clienteParaEditar.setCidade("Rio de Janeiro");
                clienteParaEditar.setEstado("RJ");
                
                dao.editar(clienteParaEditar);
                System.out.println("Cliente editado. Verificando as alterações...");
                
                Cliente clienteEditado = dao.getCliente(idParaEditar);
                System.out.println("Novo Nome: " + clienteEditado.getNome() + " | Nova Cidade: " + clienteEditado.getCidade());
            }
        } else {
            System.out.println("\n3. Teste de edição ignorado pois nenhum cliente foi encontrado no passo anterior.");
        }

        // 4. Teste de Exclusão (Delete)
        // CUIDADO: Descomente as linhas abaixo se quiser realmente excluir os dados inseridos
        /*
        if (idParaEditar != -1) {
            System.out.println("\n4. Testando Exclusão...");
            dao.excluir(idParaEditar);
            System.out.println("Cliente com ID " + idParaEditar + " excluído.");
            
            Cliente clienteExcluido = dao.getCliente(idParaEditar);
            if (clienteExcluido == null || clienteExcluido.getNome() == null) {
                System.out.println("Confirmação: Cliente não encontrado no banco.");
            }
        }
        */

        System.out.println("\n=== Fim da Rotina de Testes ===");
    }
}
