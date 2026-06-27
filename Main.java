
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Clinica clinica = new Clinica();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // convenios pre-cadastrados
        Convenio saudePlus = new Convenio("SaudePlus", 40);
        saudePlus.adicionarEspecialidade("clinica geral");
        saudePlus.adicionarEspecialidade("fisioterapia");

        Convenio vidaMais = new Convenio("VidaMais", 30);
        vidaMais.adicionarEspecialidade("psicologia");
        vidaMais.adicionarEspecialidade("nutricao");

        Convenio bemEstar = new Convenio("BemEstar", 50);
        bemEstar.adicionarEspecialidade("clinica geral");
        bemEstar.adicionarEspecialidade("psicologia");
        bemEstar.adicionarEspecialidade("nutricao");
        bemEstar.adicionarEspecialidade("fisioterapia");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== CLINICA VIDAPLENA ===");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("3 - Consultas");
            System.out.println("4 - Atendimentos");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Relatorios");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            // try/catch ()exceções
            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero.");
                opcao = -1;
                continue;
            } finally {
                // finally: log de operacao (demonstracao pedagogica do bloco)
                System.out.println("--- leitura de opcao processada ---");
            }

            switch (opcao) {
                case 1: menuPacientes(new Convenio[]{saudePlus, vidaMais, bemEstar}); break;
                case 2: menuProfissionais(); break;
                case 3: menuConsultas(); break;
                case 4: menuAtendimentos(); break;
                case 5: menuPagamentos(new Convenio[]{saudePlus, vidaMais, bemEstar}); break;
                case 6: menuRelatorios(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!");
            }
        }
        System.out.println("Sistema encerrado.");
        sc.close();
    }

    // ======== PACIENTES ========

    static void menuPacientes(Convenio[] convenios) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Complementar cadastro");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Listar ativos");
            System.out.println("5 - Desativar");
            System.out.println("6 - Listar todos (incluindo inativos)");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }
            switch (op) {
                case 1: cadastrarPaciente(convenios); break;
                case 2: complementarPaciente(convenios); break;
                case 3: buscarPaciente(); break;
                case 4: listarPacientes(); break;
                case 5: desativarPaciente(); break;
                case 6: listarTodosPacientes(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    static void cadastrarPaciente(Convenio[] convenios) {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            int idade = lerInteiro("Idade: ");
            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            Convenio conv = escolherConvenio(convenios);
            Paciente p = new Paciente(nome, cpf, idade, tel, "", conv);
            clinica.cadastrarPaciente(p);
            System.out.println("Paciente cadastrado com sucesso!");

        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Dado invalido: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de cadastro finalizada ---");
        }
    }
}