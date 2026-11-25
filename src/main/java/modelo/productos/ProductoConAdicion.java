package modelo.productos;

public class ProductoConAdicion extends ProductoDecorator {
    private final Adicion adicion;

    public ProductoConAdicion(Producto producto, Adicion adicion) {
        super(producto);
        if (adicion == null) {
            throw new IllegalArgumentException("La adición no puede ser nula");
        }
        this.adicion = adicion;
    }

    @Override
    public double precio() {
        return producto.precio() + adicion.getPrecio();
    }

    @Override
    public String descripcion() {
        return producto.descripcion() + " + " + adicion.getNombre();
    }
}
