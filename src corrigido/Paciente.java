public class Paciente extends Pessoa {

    private int idade;
    private Convenio convenio; // associacao: convenio existe independente do paciente
    private boolean ativo;

    // construtor com nome e cpf
    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.idade = 0;
        this.convenio = null;
        this.ativo = true;
    }

    // construtor com idade e telefone
    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf, telefone, "");
        setIdade(idade);
        this.convenio = null;
        this.ativo = true;
    }

    // construtor completo
    public Paciente(String nome, String cpf, int idade, String telefone, String dataNascimento, Convenio convenio) {
        super(nome, cpf, telefone, dataNascimento);
        setIdade(idade);
        this.convenio = convenio;
        this.ativo = true;
    }

    // getters
    public int getIdade() {
        return idade;
    }
    public Convenio getConvenio() {
        return convenio;
    }
    public boolean isAtivo() {
        return ativo;
    }

    // setters com validacao
    public void setIdade(int idade) {
        if (idade < 0) {
            throw new IllegalArgumentException("Idade nao pode ser negativa.");
        }
        this.idade = idade;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void desativar() {
        this.ativo = false;
    }
    public void ativar() {
        this.ativo = true;
    }

    public void complementar(int idade, String telefone) {
        setIdade(idade);
        this.telefone = telefone;
    }

    public void complementar(int idade, String telefone, Convenio convenio) {
        setIdade(idade);
        this.telefone = telefone;
        this.convenio = convenio;
    }

    public boolean temConvenio() {
        return convenio != null;
    }

    @Override
    public String exibirResumo() {
        String statusAtivo = ativo ? "Sim" : "Nao";
        String nomeConvenio = (convenio != null) ? convenio.getNome() : "Nenhum";
        return "Paciente | Nome: " + nome + " | CPF: " + cpf
                + " | Idade: " + idade + " | Tel: " + telefone
                + " | Convenio: " + nomeConvenio + " | Ativo: " + statusAtivo;
    }
}
