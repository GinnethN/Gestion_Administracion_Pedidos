package modelo.menu;
import modelo.productos.Plato;
import modelo.productos.ProductoMenu;

public class PlatoFactory extends ProductoFactory {
    @Override
    protected ProductoMenu crearProducto(String nombre, double precio) {
        return new Plato(nombre, precio);
    }
}
