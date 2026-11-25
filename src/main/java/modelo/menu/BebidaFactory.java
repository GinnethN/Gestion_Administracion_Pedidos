package modelo.menu;
import modelo.productos.Bebida;
import modelo.productos.ProductoMenu;

public class BebidaFactory extends ProductoFactory {
    @Override
    protected ProductoMenu crearProducto(String nombre, double precio) {
        return new Bebida(nombre, precio);
    }
}
