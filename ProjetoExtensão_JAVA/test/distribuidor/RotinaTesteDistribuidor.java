package distribuidor;

import distribuidor.Beans.Distribuidor;
import distribuidor.Dao.DistribuidorDao;
import java.util.List;

public class RotinaTesteDistribuidor {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Rotina de Testes para Distribuidor ===");

        DistribuidorDao dao = new DistribuidorDao();

        // 1. Teste de Inserção (Create)
        System.out.println("\n1. Testando Inserção...");
        Distribuidor novoDistribuidor = new Distribuidor();
        novoDistribuidor.setNomeEmpresa("Distribuidor Teste LTDA");
        novoDistribuidor.setCnpj("00000000000100");
        novoDistribuidor.setTelefone01("11999999999");
        novoDistribuidor.setTelefone02("11888888888");
        novoDistribuidor.setResponsavel("João Teste");
        novoDistribuidor.setSetor("Vendas");
        novoDistribuidor.setCidade("São Paulo");

        dao.inserir(novoDistribuidor);
        System.out.println("Distribuidor inserido com sucesso (verifique no banco se foi salvo).");

        // 2. Teste de Busca por Nome (Read)
        System.out.println("\n2. Testando Busca por Nome da Empresa...");
        Distribuidor distribuidorEncontrado = dao.getNomeEmpresa("Distribuidor Teste LTDA");

        int idParaEditar = -1;

        if (distribuidorEncontrado != null && distribuidorEncontrado.getIdDistribuidor() > 0) {
            System.out.println("Distribuidor encontrado!");
            System.out.println("ID: " + distribuidorEncontrado.getIdDistribuidor() +
                    " | Nome: " + distribuidorEncontrado.getNomeEmpresa() +
                    " | CNPJ: " + distribuidorEncontrado.getCnpj());
            idParaEditar = distribuidorEncontrado.getIdDistribuidor();
        } else {
            System.out.println("Nenhum distribuidor encontrado com esse nome.");
        }

        // 3. Teste de Listagem Geral (Opcional)
        System.out.println("\nListando todos os distribuidores do banco...");
        List<Distribuidor> todos = dao.getDistribuidor();
        if (todos != null) {
            System.out.println("Total de distribuidores cadastrados: " + todos.size());
        }

        // 4. Teste de Edição (Update)
        if (idParaEditar != -1) {
            System.out.println("\n4. Testando Edição...");
            Distribuidor distribuidorParaEditar = dao.getDistribuidor(idParaEditar);

            if (distribuidorParaEditar != null) {
                System.out.println("Responsável antigo: " + distribuidorParaEditar.getResponsavel());
                distribuidorParaEditar.setResponsavel("Maria Teste Alterada");
                distribuidorParaEditar.setCidade("Rio de Janeiro");

                dao.editar(distribuidorParaEditar);
                System.out.println("Distribuidor editado. Verificando as alterações...");

                Distribuidor distribuidorEditado = dao.getDistribuidor(idParaEditar);
                System.out.println("Novo Responsável: " + distribuidorEditado.getResponsavel() +
                        " | Nova Cidade: " + distribuidorEditado.getCidade());
            }
        } else {
            System.out.println(
                    "\n4. Teste de edição ignorado pois nenhum distribuidor foi encontrado no passo anterior.");
        }

        // 5. Teste de Exclusão (Delete)
        // CUIDADO: Descomente as linhas abaixo se quiser realmente excluir os dados
        // inseridos
        /*
         * if (idParaEditar != -1) {
         * System.out.println("\n5. Testando Exclusão...");
         * dao.excluir(idParaEditar);
         * System.out.println("Distribuidor com ID " + idParaEditar + " excluído.");
         * 
         * Distribuidor distribuidorExcluido = dao.getDistribuidor(idParaEditar);
         * if (distribuidorExcluido == null || distribuidorExcluido.getNomeEmpresa() ==
         * null) {
         * System.out.
         * println("Confirmação: Distribuidor não encontrado no banco após exclusão.");
         * }
         * }
         */

        System.out.println("\n=== Fim da Rotina de Testes ===");
    }
}
