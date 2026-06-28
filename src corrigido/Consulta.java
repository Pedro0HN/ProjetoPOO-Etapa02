
public class Consulta implements Agendavel, Exportavel {

    private Paciente paciente;
    private Profissional profissional;
    private String data;
    private String horario;
    private String tipo;
    private String status;
    private String motivoCancelamento;

    // sem tipo informado
    public Consulta(Paciente paciente, Profissional profissional, String data, String horario) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
        this.motivoCancelamento = "";
    }

    // com tipo informado
    public Consulta(Paciente paciente, Profissional profissional,
                    String data, String horario, String tipo) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
        this.motivoCancelamento = "";
    }
     // getters
    public Paciente getPaciente() { return paciente; }
    public Profissional getProfissional() { return profissional; }
    public String getData() { return data; }
    public String getHorario() { return horario; }
    public String getTipo() { return tipo; }
    public String getStatus() { return status; }
    public String getMotivoCancelamento() { return motivoCancelamento; }

    // setters
    public void setData(String data) { this.data = data; }
    public void setHorario(String horario) { this.horario = horario; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // ---- implementacao de Agendavel ----

    @Override
    public void agendar() {
        this.status = "agendada";
    }

    @Override
    public void cancelar(String motivo) throws OperacaoInvalidaException {
        if (status.equals("realizada")) {
            throw new OperacaoInvalidaException(
                    "Nao e possivel cancelar uma consulta ja realizada."
            );
        }
        if (status.equals("cancelada")) {
            throw new OperacaoInvalidaException(
                    "Esta consulta ja esta cancelada."
            );
        }
        this.status = "cancelada";
        this.motivoCancelamento = motivo;
    }

    @Override
    public void remarcar() throws OperacaoInvalidaException {
        if (!status.equals("agendada")) {
            throw new OperacaoInvalidaException(
                    "So e possivel remarcar consultas com status 'agendada'."
            );
        }
        this.status = "remarcada";
    }

    public void realizar() throws OperacaoInvalidaException {
        if (!status.equals("agendada")) {
            throw new OperacaoInvalidaException(
                    "So e possivel realizar consultas com status 'agendada'."
            );
        }
        this.status = "realizada";
    }

    // ---- implementacao de Exportavel ----

    @Override
    public String exportarDados() {
        return "[CONSULTA]"
                + " | CPF: " + paciente.getCpf()
                + " | Paciente: " + paciente.getNome()
                + " | Profissional: " + profissional.getNome()
                + " | Especialidade: " + profissional.getEspecialidade()
                + " | Data: " + data
                + " | Hora: " + horario
                + " | Tipo: " + tipo
                + " | Status: " + status;
    }

    public String exibirResumo() {
        return "Paciente: " + paciente.getNome()
                + " | Prof: " + profissional.getNome()
                + " | Data: " + data + " | Hora: " + horario
                + " | Tipo: " + tipo + " | Status: " + status;
    }
}
 




