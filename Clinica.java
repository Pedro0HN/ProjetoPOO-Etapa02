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
