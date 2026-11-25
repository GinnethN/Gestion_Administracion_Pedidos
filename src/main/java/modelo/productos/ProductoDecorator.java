package modelo.productos;

public abstract class ProductoDecorator implements Producto {

    protected Producto producto;

    public ProductoDecorator(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto a decorar no puede ser nulo");
        }
        this.producto = producto;
    }

    @Override
    public double precio() {
        return producto.precio();
    }

    @Override
    public String descripcion() {
        return producto.descripcion();
    }
}
