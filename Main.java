
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
        static void complementarPaciente(Convenio[] convenios) {
        try {
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            Paciente p = clinica.buscarPaciente(cpf);

            int idade = lerInteiro("Idade: ");
            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            Convenio conv = escolherConvenio(convenios);
            if (conv != null) {
                p.complementar(idade, tel, conv);
            } else {
                p.complementar(idade, tel);
            }
            System.out.println("Cadastro atualizado!");

        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de complemento finalizada ---");
        }
    }

    static void buscarPaciente() {
        try {
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            Paciente p = clinica.buscarPaciente(cpf);
            System.out.println(p.exibirResumo());
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void listarPacientes() {
        List<Paciente> lista = clinica.listarPacientes();
        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente ativo cadastrado.");
            return;
        }
        for (Paciente p : lista) System.out.println(p.exibirResumo());
    }

    static void listarTodosPacientes() {
        List<Paciente> lista = clinica.listarTodosPacientes();
        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente p : lista) System.out.println(p.exibirResumo());
    }

    static void desativarPaciente() {
        try {
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            clinica.desativarPaciente(cpf);
            System.out.println("Paciente desativado.");
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
        // ======== PROFISSIONAIS ========

    static void menuProfissionais() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }
            switch (op) {
                case 1: cadastrarProfissional(); break;
                case 2: atualizarProfissional(); break;
                case 3: listarProfissionais(); break;
                case 4: filtrarProfissionais(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    static void cadastrarProfissional() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Especialidade (clinica geral/fisioterapia/psicologia/nutricao): ");
            String esp = sc.nextLine();

            if (!Profissional.especialidadeValida(esp)) {
                System.out.println("Especialidade invalida!");
                return;
            }

            System.out.print("Registro: ");
            String reg = sc.nextLine();
            double valor = lerDouble("Valor consulta: ");

            // cria o tipo correto de profissional (heranca nivel 3)
            Profissional prof;
            switch (esp) {
                case "fisioterapia":
                    int sessoes = lerInteiro("Total de sessoes previstas: ");
                    prof = new Fisioterapeuta(nome, cpf, reg, valor, sessoes);
                    break;
                case "psicologia":
                    System.out.print("Abordagem terapeutica: ");
                    String abordagem = sc.nextLine();
                    prof = new Psicologo(nome, cpf, reg, valor, abordagem);
                    break;
                case "nutricao":
                    System.out.print("Plano alimentar: ");
                    String plano = sc.nextLine();
                    prof = new Nutricionista(nome, cpf, reg, valor, plano);
                    break;
                default:
                    System.out.print("Encaminhamento: ");
                    String enc = sc.nextLine();
                    prof = new ClinicoGeral(nome, cpf, reg, valor, enc);
                    break;
            }
                        // adiciona horarios disponiveis
            int qtd = lerInteiro("Quantos dias atende? ");
            for (int i = 0; i < qtd; i++) {
                System.out.print("Dia " + (i+1) + " (ex: segunda): ");
                String dia = sc.nextLine();
                System.out.print("Turno (manha/tarde): ");
                String turno = sc.nextLine();
                prof.adicionarHorario(new HorarioDisponivel(dia, turno));
            }

            clinica.cadastrarProfissional(prof);
            System.out.println("Profissional cadastrado!");

        } catch (IllegalArgumentException e) {
            System.out.println("Dado invalido: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de cadastro de profissional finalizada ---");
        }
    }
}
