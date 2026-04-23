package administrativo;

import administrativo.Beans.Funcionario;
import administrativo.Dao.FuncionariosDao;

public class RotinaTesteFuncionario {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Rotina de Testes para Funcionario ===");

        FuncionariosDao dao = new FuncionariosDao();

        // 1. Teste de Inserção (Create)
        System.out.println("\n1. Testando Inserção...");
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome("Funcionario Teste Rotina");
        novoFuncionario.setTelefone01("11999999999");
        novoFuncionario.setTelefone02("11888888888");
        novoFuncionario.setDataAdmissao("01/01/2023");
        novoFuncionario.setDataDemissao("");
        novoFuncionario.setNumcarteiraTrabalho("1234567");
        novoFuncionario.setRg("112223334");
        novoFuncionario.setRua("Rua Rotina");
        novoFuncionario.setNumero("123");
        novoFuncionario.setBalneario("Bairro Rotina");
        novoFuncionario.setCidade("Cidade Rotina");
        novoFuncionario.setCargoId("1");
        novoFuncionario.setUsuarioId("1");

        dao.inserir(novoFuncionario);
        System.out.println("Funcionario inserido com sucesso (verifique no banco se foi salvo).");

        // 2. Teste de Busca por Nome (Read)
        System.out.println("\n2. Testando Busca por Nome do Funcionario...");
        Funcionario funcionarioEncontrado = dao.getFuncionarioNome("Funcionario Teste Rotina");

        int idParaEditar = -1;

        if (funcionarioEncontrado != null && funcionarioEncontrado.getIdFuncionario() > 0) {
            System.out.println("Funcionario encontrado!");
            System.out.println("ID: " + funcionarioEncontrado.getIdFuncionario() +
                    " | Nome: " + funcionarioEncontrado.getNome() +
                    " | RG: " + funcionarioEncontrado.getRg());
            idParaEditar = funcionarioEncontrado.getIdFuncionario();
        } else {
            System.out.println("Nenhum funcionario encontrado com esse nome.");
        }

        // 3. Teste de Edição (Update)
        if (idParaEditar != -1) {
            System.out.println("\n3. Testando Edição...");
            Funcionario funcionarioParaEditar = dao.getFuncionario(idParaEditar);

            if (funcionarioParaEditar != null) {
                System.out.println("Telefone antigo: " + funcionarioParaEditar.getTelefone01());
                funcionarioParaEditar.setTelefone01("11777777777");
                funcionarioParaEditar.setCidade("Nova Cidade Rotina");

                dao.editar(funcionarioParaEditar);
                System.out.println("Funcionario editado. Verificando as alterações...");

                Funcionario funcionarioEditado = dao.getFuncionario(idParaEditar);
                System.out.println("Novo Telefone: " + funcionarioEditado.getTelefone01() +
                        " | Nova Cidade: " + funcionarioEditado.getCidade());
            }
        } else {
            System.out.println(
                    "\n3. Teste de edição ignorado pois nenhum funcionario foi encontrado no passo anterior.");
        }

        // 4. Teste de Exclusão (Delete)
        // CUIDADO: Descomente as linhas abaixo se quiser realmente excluir os dados inseridos
        /*
        if (idParaEditar != -1) {
            System.out.println("\n4. Testando Exclusão...");
            dao.excluir(idParaEditar);
            System.out.println("Funcionario com ID " + idParaEditar + " excluído.");
          
            Funcionario funcionarioExcluido = dao.getFuncionario(idParaEditar);
            if (funcionarioExcluido == null || funcionarioExcluido.getNome() == null) {
                System.out.println("Confirmação: Funcionario não encontrado no banco após exclusão.");
            }
        }
        */

        System.out.println("\n=== Fim da Rotina de Testes ===");
    }
}
