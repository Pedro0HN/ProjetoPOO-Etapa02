
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
    static void atualizarProfissional() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            Profissional prof = clinica.buscarProfissional(nome);

            System.out.print("Novo registro: ");
            String reg = sc.nextLine();
            double valor = lerDouble("Novo valor consulta: ");

            System.out.print("Atualizar horarios? (1-Sim / 2-Nao): ");
            int op = lerInteiro("");
            if (op == 1) {
                List<HorarioDisponivel> horarios = new ArrayList<>();
                int qtd = lerInteiro("Quantos dias? ");
                for (int i = 0; i < qtd; i++) {
                    System.out.print("Dia: ");
                    String dia = sc.nextLine();
                    System.out.print("Turno (manha/tarde): ");
                    String turno = sc.nextLine();
                    horarios.add(new HorarioDisponivel(dia, turno));
                }
                prof.atualizar(reg, valor, horarios);
            } else {
                prof.atualizar(reg, valor);
            }
            System.out.println("Profissional atualizado!");

        } catch (ProfissionalNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de atualizacao finalizada ---");
        }
    }

    static void listarProfissionais() {
        List<Profissional> lista = clinica.listarProfissionais();
        if (lista.isEmpty()) {
            System.out.println("Nenhum profissional."); return;
        }
        for (Profissional p : lista) System.out.println(p.exibirResumo());
    }

    static void filtrarProfissionais() {
        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        List<Profissional> lista = clinica.filtrarPorEspecialidade(esp);
        if (lista.isEmpty()) {
            System.out.println("Nenhum profissional com essa especialidade."); return;
        }
        for (Profissional p : lista) System.out.println(p.exibirResumo());
    }

    // ======== CONSULTAS ========

    static void menuConsultas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1 - Agendar (escolher profissional)");
            System.out.println("2 - Agendar (por especialidade)");
            System.out.println("3 - Cancelar");
            System.out.println("4 - Remarcar");
            System.out.println("5 - Listar todas");
            System.out.println("6 - Buscar por CPF");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }
            switch (op) {
                case 1: agendarComProfissional(); break;
                case 2: agendarPorEspecialidade(); break;
                case 3: cancelarConsulta(); break;
                case 4: remarcarConsulta(); break;
                case 5: listarConsultas(); break;
                case 6: buscarConsultasPorCpf(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    static void agendarComProfissional() {
        try {
            System.out.print("CPF do paciente: ");
            String cpf = sc.nextLine();
            Paciente paciente = clinica.buscarPaciente(cpf);

            System.out.print("Nome do profissional: ");
            String nomeProf = sc.nextLine();
            Profissional profissional = clinica.buscarProfissional(nomeProf);

            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();
            System.out.print("Tipo (inicial/retorno/avaliacao): ");
            String tipo = sc.nextLine();

            Consulta consulta = new Consulta(paciente, profissional, data, horario, tipo);
            clinica.agendarConsulta(consulta);
            System.out.println("Consulta agendada com sucesso!");

        } catch (PacienteNaoEncontradoException | ProfissionalNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (PacienteInativoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (HorarioIndisponivelException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de agendamento finalizada ---");
        }
    }

    static void agendarPorEspecialidade() {
        try {
            System.out.print("CPF do paciente: ");
            String cpf = sc.nextLine();
            Paciente paciente = clinica.buscarPaciente(cpf);

            System.out.print("Especialidade: ");
            String esp = sc.nextLine();
            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();

            String dia = clinica.descobrirDiaSemana(data);
            List<Profissional> disponiveis = clinica.filtrarPorEspecialidade(esp);
            Profissional escolhido = null;
            for (Profissional p : disponiveis) {
                if (p.atendeNoDia(dia) && !clinica.temConflito(p.getNome(), data, horario)) {
                    escolhido = p;
                    break;
                }
            }
            if (escolhido == null) {
                System.out.println("Nenhum profissional disponivel para essa especialidade/horario.");
                return;
            }

            Consulta consulta = new Consulta(paciente, escolhido, data, horario);
            clinica.agendarConsulta(consulta);
            System.out.println("Consulta agendada com " + escolhido.getNome() + "!");

        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (PacienteInativoException | HorarioIndisponivelException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de agendamento por especialidade finalizada ---");
        }
    }

    static void cancelarConsulta() {
        try {
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();
            System.out.print("Motivo: ");
            String motivo = sc.nextLine();
            System.out.print("Horario atual (HH:MM): ");
            String horaAtual = sc.nextLine();

            clinica.cancelarConsulta(cpf, data, horario, motivo, horaAtual);
            System.out.println("Consulta cancelada.");

        } catch (ConsultaNaoEncontradaException | OperacaoInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de cancelamento finalizada ---");
        }
    }

    static void remarcarConsulta() {
        try {
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("Data original (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario original (HH:MM): ");
            String horario = sc.nextLine();

            Consulta consulta = clinica.buscarConsulta(cpf, data, horario);

            System.out.print("Nova data (DD/MM/AAAA): ");
            String novaData = sc.nextLine();
            System.out.print("Novo horario (HH:MM): ");
            String novoHorario = sc.nextLine();

            clinica.remarcarConsulta(consulta, novaData, novoHorario);
            System.out.println("Consulta remarcada!");

        } catch (ConsultaNaoEncontradaException | OperacaoInvalidaException
                 | HorarioIndisponivelException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de remarcacao finalizada ---");
        }
    }

    static void listarConsultas() {
        List<Consulta> lista = clinica.listarConsultas();
        if (lista.isEmpty()) { System.out.println("Nenhuma consulta."); return; }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("[" + i + "] " + lista.get(i).exibirResumo());
        }
    }

    static void buscarConsultasPorCpf() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        List<Consulta> lista = clinica.buscarConsultasPorPaciente(cpf);
        if (lista.isEmpty()) { System.out.println("Nenhuma consulta encontrada."); return; }
        for (Consulta c : lista) System.out.println(c.exibirResumo());
    }


    // ======== ATENDIMENTOS ========

    static void menuAtendimentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }
            if (op == 1) registrarAtendimento();
        }
    }

    static void registrarAtendimento() {
        try {
            listarConsultas();
            int idx = lerInteiro("Indice da consulta: ");
            List<Consulta> lista = clinica.listarConsultas();

            if (idx < 0 || idx >= lista.size()) {
                System.out.println("Indice invalido.");
                return;
            }

            Consulta consulta = lista.get(idx);
            System.out.print("Observacoes: ");
            String obs = sc.nextLine();
            System.out.print("Diagnostico: ");
            String diag = sc.nextLine();
            System.out.print("Data do registro (DD/MM/AAAA): ");
            String dataReg = sc.nextLine();

            Prontuario prontuario = new Prontuario(obs, diag, dataReg);

            int qtdProc = lerInteiro("Quantos procedimentos? ");
            for (int i = 0; i < qtdProc; i++) {
                System.out.print("Procedimento " + (i+1) + ": ");
                prontuario.adicionarProcedimento(sc.nextLine());
            }

            Atendimento atendimento = new Atendimento(consulta, prontuario);
            clinica.registrarAtendimento(atendimento);
            System.out.println("Atendimento registrado!");
            System.out.println(atendimento.exibirResumo());

        } catch (OperacaoInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- operacao de atendimento finalizada ---");
        }
    }

    // ======== PAGAMENTOS ========

    static void menuPagamentos(Convenio[] convenios) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Registrar pagamento");
            System.out.println("2 - Listar pagamentos");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }
            switch (op) {
                case 1: registrarPagamento(convenios); break;
                case 2: listarPagamentos(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    static void registrarPagamento(Convenio[] convenios) {
        try {
            listarConsultas();
            int idx = lerInteiro("Indice da consulta: ");
            List<Consulta> lista = clinica.listarConsultas();

            if (idx < 0 || idx >= lista.size()) {
                System.out.println("Indice invalido.");
                return;
            }

            Consulta consulta = lista.get(idx);
            double valorBase = lerDouble("Valor base: ");

            System.out.print("Tipo (dinheiro/cartao/convenio): ");
            String tipo = sc.nextLine();

            Pagamento pagamento;

            switch (tipo) {
                case "dinheiro":
                    pagamento = new PagamentoDinheiro(consulta, valorBase);
                    break;
                case "cartao":
                    int parcelas = lerInteiro("Parcelas (1-6): ");
                    pagamento = new PagamentoCartao(consulta, valorBase, parcelas);
                    break;
                case "convenio":
                    Convenio conv = escolherConvenio(convenios);
                    if (conv == null) {
                        System.out.println("Nenhum convenio selecionado.");
                        return;
                    }
                    pagamento = new PagamentoConvenio(consulta, valorBase, conv);
                    break;
                default:
                    throw new PagamentoInvalidoException(
                            "Tipo de pagamento invalido: " + tipo
                                    + ". Use: dinheiro, cartao ou convenio."
                    );
            }

            clinica.registrarPagamento(pagamento);
            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());

        } catch (PagamentoInvalidoException | ConvenioNaoCobreException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("--- Operacao de pagamento finalizada ---");
        }
    }

    static void listarPagamentos() {
        List<Pagamento> lista = clinica.listarPagamentos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pagamento."); return;
        }
        for (Pagamento p : lista) System.out.println(p.exibirResumo());
    }

    // ======== RELATORIOS ========

    static void menuRelatorios() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Todas as pessoas (ligacao dinamica)");
            System.out.println("2 - Resumo financeiro");
            System.out.println("3 - Exportar dados das consultas");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }
            switch (op) {
                case 1: clinica.exibirTodasPessoas(); break;
                case 2: clinica.exibirResumoFinanceiro(); break;
                case 3: exportarConsultas(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    static void exportarConsultas() {
        List<Consulta> lista = clinica.listarConsultas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma consulta para exportar.");
            return;
        }
        System.out.println("\n=== EXPORTACAO DE DADOS ===");
        // trata Consulta como Exportavel
        for (Exportavel e : lista) {
            System.out.println(e.exportarDados());
        }
    }

    
}

