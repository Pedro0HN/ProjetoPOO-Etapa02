public abstract class Pessoa {


    protected String nome;
    protected String cpf;
    protected String telefone;
    protected String dataNascimento;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        setCpf(cpf);
        this.telefone = "";
        this.dataNascimento = "";
    }

    public Pessoa(String nome, String cpf, String telefone, String dataNascimento) {
        this.nome = nome;
        setCpf(cpf);
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    // getters
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getDataNascimento() { return dataNascimento; }

    // setters
    public void setNome(String nome) { this.nome = nome; }


    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
  
