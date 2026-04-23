package esquadria;

import esquadria.Beans.Esquadria;
import esquadria.Dao.EsquadriaDao;

public class RotinaTesteEsquadria {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Rotina de Testes para Esquadria ===");

        EsquadriaDao dao = new EsquadriaDao();

        // 1. Teste de Inserção (Create)
        System.out.println("\n1. Testando Inserção...");
        Esquadria novaEsquadria = new Esquadria();
        novaEsquadria.setNomeEsquadria("Esquadria Teste Rotina");
        novaEsquadria.setDescricao("Descricao Teste Rotina");
        novaEsquadria.setPreco("150.00");
        novaEsquadria.setDistribuidorId("1");

        dao.inserir(novaEsquadria);
        System.out.println("Esquadria inserida com sucesso (verifique no banco se foi salva).");

        // 2. Teste de Busca por Nome (Read)
        System.out.println("\n2. Testando Busca por Nome da Esquadria...");
        Esquadria esquadriaEncontrada = dao.getPesquisarNome("Esquadria Teste Rotina");

        int idParaEditar = -1;

        if (esquadriaEncontrada != null && esquadriaEncontrada.getIdEsquadria() > 0) {
            System.out.println("Esquadria encontrada!");
            System.out.println("ID: " + esquadriaEncontrada.getIdEsquadria() +
                    " | Nome: " + esquadriaEncontrada.getNomeEsquadria() +
                    " | Preço: " + esquadriaEncontrada.getPreco());
            idParaEditar = esquadriaEncontrada.getIdEsquadria();
        } else {
            System.out.println("Nenhuma esquadria encontrada com esse nome.");
        }

        // 3. Teste de Edição (Update)
        if (idParaEditar != -1) {
            System.out.println("\n3. Testando Edição...");
            Esquadria esquadriaParaEditar = dao.getEsquadria(idParaEditar);

            if (esquadriaParaEditar != null) {
                System.out.println("Preço antigo: " + esquadriaParaEditar.getPreco());
                esquadriaParaEditar.setPreco("300.00");
                esquadriaParaEditar.setDescricao("Nova Descricao Rotina");

                dao.editar(esquadriaParaEditar);
                System.out.println("Esquadria editada. Verificando as alterações...");

                Esquadria esquadriaEditada = dao.getEsquadria(idParaEditar);
                System.out.println("Novo Preço: " + esquadriaEditada.getPreco() +
                        " | Nova Descrição: " + esquadriaEditada.getDescricao());
            }
        } else {
            System.out.println(
                    "\n3. Teste de edição ignorado pois nenhuma esquadria foi encontrada no passo anterior.");
        }

        // 4. Teste de Exclusão (Delete)
        // CUIDADO: Descomente as linhas abaixo se quiser realmente excluir os dados inseridos
        /*
        if (idParaEditar != -1) {
            System.out.println("\n4. Testando Exclusão...");
            dao.excluir(idParaEditar);
            System.out.println("Esquadria com ID " + idParaEditar + " excluída.");
          
            Esquadria esquadriaExcluida = dao.getEsquadria(idParaEditar);
            if (esquadriaExcluida == null || esquadriaExcluida.getNomeEsquadria() == null) {
                System.out.println("Confirmação: Esquadria não encontrada no banco após exclusão.");
            }
        }
        */

        System.out.println("\n=== Fim da Rotina de Testes ===");
    }
}
