package modelo.productos;

public abstract class ProductoMenu implements Producto {
    protected String nombre;
    protected double precioBase;

    public ProductoMenu(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    @Override
    public double precio() {
        return precioBase;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}
