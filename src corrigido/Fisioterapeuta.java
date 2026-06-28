public class Fisioterapeuta extends Profissional {

    private int totalSessoesPrevistas;

    public Fisioterapeuta(String nome, String cpf) {
        super(nome, cpf, "fisioterapia");
        this.totalSessoesPrevistas = 0;
    }

    public Fisioterapeuta(String nome, String cpf, String registro, double valor) {
        super(nome, cpf, "fisioterapia", registro, valor);
        this.totalSessoesPrevistas = 0;
    }

    public Fisioterapeuta(String nome, String cpf, String registro, double valor, int sessoes) {
        super(nome, cpf, "fisioterapia", registro, valor);
        this.totalSessoesPrevistas = sessoes;
    }

    public int getTotalSessoesPrevistas() {
        return totalSessoesPrevistas;
    }
    public void setTotalSessoesPrevistas(int sessoes) {
        this.totalSessoesPrevistas = sessoes;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.getProntuario().adicionarObservacao(
                "Sessoes previstas no plano: " + totalSessoesPrevistas
        );
    }

    @Override
    public String exibirResumo() {
        return dadosBase() + " | Sessoes previstas: " + totalSessoesPrevistas;
    }
}

// ---- Psicologo ----
class Psicologo extends Profissional {

    private String abordagem;

    public Psicologo(String nome, String cpf) {
        super(nome, cpf, "psicologia");
        this.abordagem = "";
    }

    public Psicologo(String nome, String cpf, String registro, double valor) {
        super(nome, cpf, "psicologia", registro, valor);
        this.abordagem = "";
    }

    public Psicologo(String nome, String cpf, String registro, double valor, String abordagem) {
        super(nome, cpf, "psicologia", registro, valor);
        this.abordagem = abordagem;
    }

    public String getAbordagem() {
        return abordagem;
    }
    public void setAbordagem(String abordagem) {
        this.abordagem = abordagem;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.getProntuario().adicionarObservacao(
                "Abordagem terapeutica: " + abordagem
        );
    }

    @Override
    public String exibirResumo() {
        return dadosBase() + " | Abordagem: " + abordagem;
    }
}

// ---- Nutricionista ----
class Nutricionista extends Profissional {

    private String planoAlimentar;

    public Nutricionista(String nome, String cpf) {
        super(nome, cpf, "nutricao");
        this.planoAlimentar = "";
    }

    public Nutricionista(String nome, String cpf, String registro, double valor) {
        super(nome, cpf, "nutricao", registro, valor);
        this.planoAlimentar = "";
    }

    public Nutricionista(String nome, String cpf, String registro, double valor, String plano) {
        super(nome, cpf, "nutricao", registro, valor);
        this.planoAlimentar = plano;
    }

    public String getPlanoAlimentar() {
        return planoAlimentar;
    }
    public void setPlanoAlimentar(String plano) {
        this.planoAlimentar = plano;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.getProntuario().adicionarObservacao(
                "Plano alimentar: " + planoAlimentar
        );
    }

    @Override
    public String exibirResumo() {
        return dadosBase() + " | Plano alimentar: " + planoAlimentar;
    }
}

// ---- ClinicoGeral ----
class ClinicoGeral extends Profissional {

    private String encaminhamento;

    public ClinicoGeral(String nome, String cpf) {
        super(nome, cpf, "clinica geral");
        this.encaminhamento = "";
    }

    public ClinicoGeral(String nome, String cpf, String registro, double valor) {
        super(nome, cpf, "clinica geral", registro, valor);
        this.encaminhamento = "";
    }

    public ClinicoGeral(String nome, String cpf, String registro, double valor, String encaminhamento) {
        super(nome, cpf, "clinica geral", registro, valor);
        this.encaminhamento = encaminhamento;
    }

    public String getEncaminhamento() {
        return encaminhamento;
    }
    public void setEncaminhamento(String enc) {
        this.encaminhamento = enc;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (!encaminhamento.isEmpty()) {
            atendimento.getProntuario().adicionarObservacao(
                    "Encaminhamento: " + encaminhamento
            );
        }
    }

    @Override
    public String exibirResumo() {
        return dadosBase() + " | Encaminhamento: " + encaminhamento;
    }
}