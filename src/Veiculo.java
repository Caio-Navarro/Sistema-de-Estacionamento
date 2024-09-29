import java.time.LocalTime;

public class Veiculo {
    private String placa;
    private String tipo;
    private LocalTime horaEntrada;

    public Veiculo(String placa, String tipo, LocalTime horaEntrada) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaEntrada = horaEntrada;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

}
