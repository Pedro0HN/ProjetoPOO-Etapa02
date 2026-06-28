import java.util.ArrayList;
import java.util.List;

public abstract class Profissional extends Pessoa {

    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;

    private List<HorarioDisponivel> horariosDisponiveis;

    // especialidades validas da clinica
    private static final String[] ESPECIALIDADES_VALIDAS = {
            "clinica geral", "fisioterapia", "psicologia", "nutricao"
    };

    public Profissional(String nome, String cpf, String especialidade) {
        super(nome, cpf);
        this.especialidade = especialidade;
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.horariosDisponiveis = new ArrayList<>();
    }

    public Profissional(String nome, String cpf, String especialidade,
                        String registroProfissional, double valorConsulta) {
        super(nome, cpf);
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        setValorConsulta(valorConsulta);
        this.horariosDisponiveis = new ArrayList<>();
    }

    public Profissional(String nome, String cpf, String telefone, String dataNascimento,
                        String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, cpf, telefone, dataNascimento);
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        setValorConsulta(valorConsulta);
        this.horariosDisponiveis = new ArrayList<>();
    }

    // getters
    public String getEspecialidade() {
        return especialidade;
    }
    public String getRegistroProfissional() {
        return registroProfissional;
    }
    public double getValorConsulta() {
        return valorConsulta;
    }
    public List<HorarioDisponivel> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    // setters
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public void setRegistroProfissional(String registro) {
        this.registroProfissional = registro;
    }

    public void setValorConsulta(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor da consulta nao pode ser negativo.");
        }
        this.valorConsulta = valor;
    }

    public void atualizar(String registro, double valor) {
        this.registroProfissional = registro;
        setValorConsulta(valor);
    }

    public void atualizar(String registro, double valor, List<HorarioDisponivel> horarios) {
        this.registroProfissional = registro;
        setValorConsulta(valor);
        this.horariosDisponiveis = new ArrayList<>(horarios);
    }



   