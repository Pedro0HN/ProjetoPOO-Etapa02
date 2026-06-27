public interface Agendavel {
    // contrato de agendamento
    void agendar();
    void cancelar(String motivo) throws OperacaoInvalidaException;
    void remarcar() throws OperacaoInvalidaException;
}