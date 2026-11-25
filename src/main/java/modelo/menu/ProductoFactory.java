package modelo.menu;
import modelo.productos.ProductoMenu;

public abstract class ProductoFactory {
    public ProductoMenu registrar(String nombre, double precio) {
        if (nombre == null || nombre.isBlank() || precio < 0) {
            throw new IllegalArgumentException("Datos de producto inválidos");
        }
        return crearProducto(nombre, precio);
    }

    protected abstract ProductoMenu crearProducto(String nombre, double precio);
}
