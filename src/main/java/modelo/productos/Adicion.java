package modelo.productos;

public class Adicion {
    private final String nombre;
    private final double precio;

    public Adicion(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " (+$" + precio + ")";
    }
}
