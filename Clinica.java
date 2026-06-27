import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Clinica {

    // HashMap: busca rapida por CPF -> Paciente
    private Map<String, Paciente> mapaPacientes = new HashMap<>();

    // HashMap: busca rapida por nome -> Profissional
    private Map<String, Profissional> mapaProfissionais = new HashMap<>();

    // HashSet: garante unicidade de CPFs antes de inserir
    private Set<String> cpfsCadastrados = new HashSet<>();

    // Lista unificada de todas as pessoas (para relatorio geral com ligacao dinamica)
    private List<Pessoa> todasPessoas = new ArrayList<>();

    // Listas de operacoes
    private List<Consulta> consultas = new ArrayList<>();
    private List<Atendimento> atendimentos = new ArrayList<>();
    private List<Pagamento> pagamentos = new ArrayList<>();
    private List<Double> multas = new ArrayList<>();

    // ---- PACIENTES ----

    public void cadastrarPaciente(Paciente paciente)
            throws PacienteNaoEncontradoException {
        // HashSet verifica duplicidade de CPF
        if (cpfsCadastrados.contains(paciente.getCpf())) {
            throw new PacienteNaoEncontradoException(
                    "CPF ja cadastrado: " + paciente.getCpf()
            );
        }
        cpfsCadastrados.add(paciente.getCpf());
        mapaPacientes.put(paciente.getCpf(), paciente);
        todasPessoas.add(paciente);
    }

    public Paciente buscarPaciente(String cpf)
            throws PacienteNaoEncontradoException {
        Paciente p = mapaPacientes.get(cpf); // busca O(1) no HashMap
        if (p == null) {
            throw new PacienteNaoEncontradoException(
                    "Paciente nao encontrado para CPF: " + cpf
            );
        }
        return p;
    }

    public void desativarPaciente(String cpf)
            throws PacienteNaoEncontradoException {
        Paciente p = buscarPaciente(cpf);
        p.desativar();
    }

    public List<Paciente> listarPacientes() {
        List<Paciente> ativos = new ArrayList<>();
        for (Paciente p : mapaPacientes.values()) {
            if (p.isAtivo()) ativos.add(p);
        }
        return ativos;
    }

    public List<Paciente> listarTodosPacientes() {
        return new ArrayList<>(mapaPacientes.values());
    }

    // ---- PROFISSIONAIS ----

    public void cadastrarProfissional(Profissional profissional) {
        mapaProfissionais.put(profissional.getNome(), profissional);
        todasPessoas.add(profissional);
    }

    public Profissional buscarProfissional(String nome)
            throws ProfissionalNaoEncontradoException {
        Profissional p = mapaProfissionais.get(nome); // busca O(1) no HashMap
        if (p == null) {
            throw new ProfissionalNaoEncontradoException(
                    "Profissional nao encontrado: " + nome
            );
        }
        return p;
    }

    public List<Profissional> listarProfissionais() {
        return new ArrayList<>(mapaProfissionais.values());
    }

    public List<Profissional> filtrarPorEspecialidade(String especialidade) {
        List<Profissional> resultado = new ArrayList<>();
        for (Profissional p : mapaProfissionais.values()) {
            if (p.getEspecialidade().equals(especialidade)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // ---- CONSULTAS ----

    public void agendarConsulta(Consulta consulta)
            throws PacienteInativoException, HorarioIndisponivelException {

        Paciente paciente = consulta.getPaciente();
        Profissional profissional = consulta.getProfissional();

        if (!paciente.isAtivo()) {
            throw new PacienteInativoException(
                    "Paciente " + paciente.getNome() + " esta inativo."
            );
        }

        String diaSemana = descobrirDiaSemana(consulta.getData());
        if (!profissional.atendeNoDia(diaSemana)) {
            throw new HorarioIndisponivelException(
                    "Profissional nao atende no dia: " + diaSemana
            );
        }

        if (temConflito(profissional.getNome(), consulta.getData(), consulta.getHorario())) {
            throw new HorarioIndisponivelException(
                    "Horario ja ocupado: " + consulta.getData() + " " + consulta.getHorario()
            );
        }

        consultas.add(consulta);
    }

    public Consulta buscarConsulta(String cpf, String data, String horario)
            throws ConsultaNaoEncontradaException {
        for (Consulta c : consultas) {
            if (c.getPaciente().getCpf().equals(cpf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)) {
                return c;
            }
        }
        throw new ConsultaNaoEncontradaException(
                "Consulta nao encontrada para CPF " + cpf + " em " + data + " " + horario
        );
    }

    public void cancelarConsulta(String cpf, String data, String horario,
                                 String motivo, String horaAtual)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {

        Consulta c = buscarConsulta(cpf, data, horario);
        c.cancelar(motivo); // lanca OperacaoInvalidaException se nao puder cancelar

        // calculo de multa: cancelamento com menos de 2h de antecedencia
        try {
            int hConsulta = Integer.parseInt(horario.substring(0, 2));
            int hAgora    = Integer.parseInt(horaAtual.substring(0, 2));
            if ((hConsulta - hAgora) < 2) {
                multas.add(50.0);
                System.out.println("Multa de R$50,00 aplicada por cancelamento tardio.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Aviso: nao foi possivel calcular multa (horario invalido).");
        }
    }

    public void remarcarConsulta(Consulta consulta, String novaData, String novoHorario)
            throws OperacaoInvalidaException, HorarioIndisponivelException {

        String diaSemana = descobrirDiaSemana(novaData);
        if (!consulta.getProfissional().atendeNoDia(diaSemana)) {
            throw new HorarioIndisponivelException(
                    "Profissional nao atende em: " + diaSemana
            );
        }
        if (temConflito(consulta.getProfissional().getNome(), novaData, novoHorario)) {
            throw new HorarioIndisponivelException(
                    "Horario ja ocupado: " + novaData + " " + novoHorario
            );
        }

        consulta.remarcar(); // muda status para remarcada

        // cria nova consulta com nova data/horario
        Consulta nova = new Consulta(
                consulta.getPaciente(),
                consulta.getProfissional(),
                novaData,
                novoHorario,
                consulta.getTipo()
        );
        consultas.add(nova);
    }

    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }

    public List<Consulta> buscarConsultasPorPaciente(String cpf) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : consultas) {
            if (c.getPaciente().getCpf().equals(cpf)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // ---- ATENDIMENTOS ----

    public void registrarAtendimento(Atendimento atendimento)
            throws OperacaoInvalidaException {
        Consulta c = atendimento.getConsulta();
        if (!c.getStatus().equals("agendada")) {
            throw new OperacaoInvalidaException(
                    "So e possivel registrar atendimento em consulta agendada. Status atual: "
                            + c.getStatus()
            );
        }
        c.realizar();

        // registra info especifica da especialidade do profissional (polimorfismo)
        c.getProfissional().registrarEspecifico(atendimento);

        atendimentos.add(atendimento);
    }

    public List<Atendimento> listarAtendimentos() {
        return new ArrayList<>(atendimentos);
    }

    // ---- PAGAMENTOS ----

    public void registrarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public List<Pagamento> listarPagamentos() {
        return new ArrayList<>(pagamentos);
    }

    // ---- RELATORIOS ----

    // ligacao dinamica: percorre lista de Pessoa e chama exibirResumo() de cada uma
    public void exibirTodasPessoas() {
        System.out.println("\n=== CADASTROS GERAIS ===");
        for (Pessoa p : todasPessoas) {
            System.out.println(p.exibirResumo()); // comportamento correto por tipo
            // dynamic casting: exibe info extra se for Paciente ou Profissional
            if (p instanceof Paciente) {
                Paciente pac = (Paciente) p;
                System.out.println("  >> Paciente ativo: " + pac.isAtivo());
            } else if (p instanceof Profissional) {
                Profissional prof = (Profissional) p;
                System.out.println("  >> Valor consulta: R$" + prof.getValorConsulta());
            }
            System.out.println("---");
        }
    }

    public void exibirResumoFinanceiro() {
        long realizadas  = consultas.stream().filter(c -> c.getStatus().equals("realizada")).count();
        long canceladas  = consultas.stream().filter(c -> c.getStatus().equals("cancelada")).count();
        double faturado  = pagamentos.stream().mapToDouble(Pagamento::calcularValorFinal).sum();
        double totalMultas = multas.stream().mapToDouble(Double::doubleValue).sum();

        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.println("Atendimentos realizados: " + realizadas);
        System.out.println("Cancelamentos: " + canceladas);
        System.out.printf("Total faturado: R$%.2f%n", faturado);
        System.out.printf("Total em multas: R$%.2f%n", totalMultas);
    }

    // ---- AUXILIARES ----

    public boolean temConflito(String nomeProf, String data, String horario) {
        for (Consulta c : consultas) {
            if (c.getProfissional().getNome().equals(nomeProf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)
                    && c.getStatus().equals("agendada")) {
                return true;
            }
        }
        return false;
    }

    public String sugerirHorario(String nomeProf, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste = (h < 10 ? "0" + h : h) + ":00";
            if (!temConflito(nomeProf, data, teste)) return teste;
        }
        return "";
    }

    public String descobrirDiaSemana(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));

        if (mes < 3) { mes += 12; ano--; }

        int k = ano % 100;
        int j = ano / 100;
        int resultado = (dia + (13 * (mes + 1)) / 5 + k + k/4 + j/4 - 2*j) % 7;
        if (resultado < 0) resultado += 7;

        String[] nomes = {"sabado","domingo","segunda","terca","quarta","quinta","sexta"};
        return nomes[resultado];
    }
}