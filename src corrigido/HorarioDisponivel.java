public class HorarioDisponivel {

    private String diaSemana;
    private String turno;

    public HorarioDisponivel(String diaSemana, String turno) {
        this.diaSemana = diaSemana;
        setTurno(turno);
    }

    // getters
    public String getDiaSemana() {
        return diaSemana;
    }
    public String getTurno() {
        return turno;
    }

    // setters
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setTurno(String turno) {
        if (!turno.equals("manha") && !turno.equals("tarde")) {
            throw new IllegalArgumentException("Turno deve ser 'manha' ou 'tarde'.");
        }
        this.turno = turno;
    }

    @Override
    public String toString() {
        return diaSemana + " (" + turno + ")";
    }
}
