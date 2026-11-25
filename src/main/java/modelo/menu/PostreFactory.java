package modelo.menu;
import modelo.productos.Postre;
import modelo.productos.ProductoMenu;

public class PostreFactory extends ProductoFactory{
    @Override
    protected ProductoMenu crearProducto(String nombre, double precio) {
        return new Postre(nombre, precio);
    }
}
