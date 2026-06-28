public class Atendimento implements Exportavel {

    private Consulta consulta;
    private Prontuario prontuario;
    // sobrecarga
    public Atendimento(Consulta consulta, String observacoes, String dataRegistro) {
        this.consulta = consulta;
        this.prontuario = new Prontuario(observacoes, dataRegistro);
    }

    // Sobrecarha
    public Atendimento(Consulta consulta, String observacoes,
                       String diagnostico, String dataRegistro) {
        this.consulta = consulta;
        this.prontuario = new Prontuario(observacoes, diagnostico, dataRegistro);
    }


    public Atendimento(Consulta consulta, Prontuario prontuario) {
        this.consulta = consulta;
        this.prontuario = prontuario;
    }

    // getters
    public Consulta getConsulta() {
        return consulta;
    }
    public Prontuario getProntuario() {
        return prontuario;
    }

    public String exibirResumo() {
        return "Atendimento | Paciente: " + consulta.getPaciente().getNome()
                + " | Prof: " + consulta.getProfissional().getNome()
                + "\n" + prontuario.exibirResumo();
    }

    @Override
    public String exportarDados() {
        return "[ATENDIMENTO]"
                + " | CPF: " + consulta.getPaciente().getCpf()
                + " | Profissional: " + consulta.getProfissional().getNome()
                + " | Data: " + consulta.getData()
                + " | Diagnostico: " + prontuario.getDiagnostico()
                + " | Procedimentos: " + String.join(", ", prontuario.getProcedimentos());
    }
}