import java.util.ArrayList;
import java.util.List;

public class Convenio {

    private String nome;
    private double percentualCobertura;
    private List<String> especialidadesCobertas;

    public Convenio(String nome, double percentualCobertura) {
        this.nome = nome;
        setPercentualCobertura(percentualCobertura);
        this.especialidadesCobertas = new ArrayList<>();
    }

    public Convenio(String nome, double percentualCobertura, List<String> especialidades) {
        this.nome = nome;
        setPercentualCobertura(percentualCobertura);
        this.especialidadesCobertas = new ArrayList<>(especialidades);
    }

    // getters
    public String getNome() {
        return nome;
    }
    public double getPercentualCobertura() {
        return percentualCobertura;
    }
    public List<String> getEspecialidadesCobertas() {
        return especialidadesCobertas;
    }

    // setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPercentualCobertura(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 100.");
        }
        this.percentualCobertura = percentual;
    }





    public void adicionarEspecialidade(String especialidade) {
        if (!especialidadesCobertas.contains(especialidade)) {
            especialidadesCobertas.add(especialidade);
        }
    }




    public boolean cobreespecialidade(String especialidade) {
        return especialidadesCobertas.contains(especialidade);
    }

    @Override
    public String toString() {
        return nome + " (" + percentualCobertura + "% de cobertura)";
    }
}