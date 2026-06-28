// Classe abstrata
public abstract class Pagamento implements Exportavel {

    private Consulta consulta;
    private double valorBase;
    private String tipoPagamento;

    public Pagamento(Consulta consulta, double valorBase, String tipoPagamento)
            throws PagamentoInvalidoException {
        if (valorBase < 0) {
            throw new PagamentoInvalidoException("Valor do pagamento não pode ser negativo.");
        }
        this.consulta = consulta;
        this.valorBase = valorBase;
        this.tipoPagamento = tipoPagamento;
    }

    // getters
    public Consulta getConsulta() {
        return consulta;
    }
    public double getValorBase() {
        return valorBase;
    }
    public String getTipoPagamento() {
        return tipoPagamento;
    }

    // calcular valor final
    public abstract double calcularValorFinal();

    public String exibirResumo() {
        double final_ = Math.round(calcularValorFinal() * 100.0) / 100.0;
        return "Pagamento | Tipo: " + tipoPagamento
                + " | Valor base: R$" + valorBase
                + " | Valor final: R$" + final_;
    }

    @Override
    public String exportarDados() {
        return "[PAGAMENTO]"
                + " | Tipo: " + tipoPagamento
                + " | Valor base: R$" + valorBase
                + " | Valor final: R$" + Math.round(calcularValorFinal() * 100.0) / 100.0
                + " | Paciente: " + consulta.getPaciente().getNome();
    }
}

// Pagamento em dinheiro ou pix te, 5% de desconto
class PagamentoDinheiro extends Pagamento {

    public PagamentoDinheiro(Consulta consulta, double valorBase)
            throws PagamentoInvalidoException {
        super(consulta, valorBase, "dinheiro");
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * 0.95; // 5% de desconto
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Desconto: 5%";
    }
}

//  se pagar com cartão o parcelamento teme em ate 6x, taxa de 2,5% por parcela acima de 3
class PagamentoCartao extends Pagamento {

    private int parcelas;

    public PagamentoCartao(Consulta consulta, double valorBase, int parcelas)
            throws PagamentoInvalidoException {
        super(consulta, valorBase, "cartao");
        if (parcelas < 1 || parcelas > 6) {
            throw new PagamentoInvalidoException(
                    "Parcelas devem ser entre 1 e 6. Informado: " + parcelas
            );
        }
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return parcelas;
    }

    @Override
    public double calcularValorFinal() {
        if (parcelas <= 3) {
            return getValorBase();
        }
        int parcelasExtras = parcelas - 3;
        double taxa = parcelasExtras * 0.025;
        return getValorBase() * (1 + taxa);
    }

    @Override
    public String exibirResumo() {
        double valorFinal = Math.round(calcularValorFinal() * 100.0) / 100.0;
        double valorParcela = Math.round((valorFinal / parcelas) * 100.0) / 100.0;
        return super.exibirResumo()
                + " | Parcelas: " + parcelas + "x de R$" + valorParcela;
    }
}

//Pagamento por convenio
class PagamentoConvenio extends Pagamento {

    private Convenio convenio;

    public PagamentoConvenio(Consulta consulta, double valorBase, Convenio convenio)
            throws PagamentoInvalidoException, ConvenioNaoCobreException {
        super(consulta, valorBase, "convenio");
        if (convenio == null) {
            throw new PagamentoInvalidoException("Paciente nao possui convenio cadastrado.");
        }
        // vê se o convenio cobre a especialidade da consulta
        String especialidade = consulta.getProfissional().getEspecialidade();
        if (!convenio.cobreespecialidade(especialidade)) {
            throw new ConvenioNaoCobreException(
                    "O convenio '" + convenio.getNome()
                            + "' nao cobre a especialidade: " + especialidade
            );
        }
        this.convenio = convenio;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    @Override
    public double calcularValorFinal() {
        double cobertura = getValorBase() * convenio.getPercentualCobertura() / 100;
        double valor = getValorBase() - cobertura;
        return Math.max(valor, 0);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo()
                + " | Convenio: " + convenio.getNome()
                + " (" + convenio.getPercentualCobertura() + "% cobertura)";
    }
}