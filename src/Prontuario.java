import java.util.ArrayList;
import java.util.List;

public class Prontuario {

    private String observacoes;
    private String diagnostico;
    private List<String> procedimentos;
    private String dataRegistro;

    public Prontuario(String observacoes, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = "";
        this.procedimentos = new ArrayList<>();
        this.dataRegistro = dataRegistro;
    }

    public Prontuario(String observacoes, String diagnostico, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new ArrayList<>();
        this.dataRegistro = dataRegistro;
    }

    public Prontuario(String observacoes, String diagnostico,
                      List<String> procedimentos, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new ArrayList<>(procedimentos);
        this.dataRegistro = dataRegistro;
    }

    // getters
    public String getObservacoes() { return observacoes; }
    public String getDiagnostico() { return diagnostico; }
    public List<String> getProcedimentos() { return procedimentos; }
    public String getDataRegistro() { return dataRegistro; }

    // setters
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setDataRegistro(String data) { this.dataRegistro = data; }

    public void adicionarProcedimento(String procedimento) {
        procedimentos.add(procedimento);
    }

    // usado pelas especializacoes de Profissional para adicionar infomação especifica
    public void adicionarObservacao(String extra) {
        this.observacoes = this.observacoes + "\n" + extra;
    }

    public String exibirResumo() {
        String resumo = "Data: " + dataRegistro + "\nObservacoes: " + observacoes;
        if (!diagnostico.isEmpty()) {
            resumo += "\nDiagnostico: " + diagnostico;
        }
        if (!procedimentos.isEmpty()) {
            resumo += "\nProcedimentos: " + String.join(", ", procedimentos);
        }
        return resumo;
    }
}