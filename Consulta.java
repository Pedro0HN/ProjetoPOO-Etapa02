
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

