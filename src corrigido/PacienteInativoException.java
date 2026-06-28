public class PacienteInativoException extends Exception {
    public PacienteInativoException(String mensagem) {
        super(mensagem);
    }
    public PacienteInativoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Paciente nao encontrado ----
class PacienteNaoEncontradoException extends Exception {
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public PacienteNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Profissional nao encontrado ----
class ProfissionalNaoEncontradoException extends Exception {
    public ProfissionalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public ProfissionalNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Horario indisponivel ---
class HorarioIndisponivelException extends Exception {
    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
    public HorarioIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Consulta nao encontrada ----
class ConsultaNaoEncontradaException extends Exception {
    public ConsultaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    public ConsultaNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Operacao invalida ----
class OperacaoInvalidaException extends Exception {
    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
    public OperacaoInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Pagamento invalido ----
class PagamentoInvalidoException extends Exception {
    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
    public PagamentoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

// ---- Convenio nao cobre a especialidade da consulta ----
class ConvenioNaoCobreException extends Exception {
    public ConvenioNaoCobreException(String mensagem) {
        super(mensagem);
    }
    public ConvenioNaoCobreException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
 