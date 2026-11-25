package modelo.pedidos;

import java.time.LocalDateTime;

public class Pago {
    private final String metodo;
    private final double monto;
    private final LocalDateTime fechaHora;

    public Pago(String metodo, double monto) {
        this.metodo = metodo;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
    }

    public String getMetodo() { return metodo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}
