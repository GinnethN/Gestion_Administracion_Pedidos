import modelo.menu.MenuConfigurable;
import modelo.productos.Bebida;
import modelo.productos.Plato;
import modelo.productos.Postre;
import modelo.productos.ProductoMenu;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactoryMethodTest {
    @Test
    void deberiaCrearProductosSegunLaFabrica() {
        MenuConfigurable menu = new MenuConfigurable();

        ProductoMenu bebida = menu.obtenerFabrica("BEBIDA").registrar("Jugo", 5000);
        ProductoMenu plato = menu.obtenerFabrica("PLATO").registrar("Pasta", 15000);
        ProductoMenu postre = menu.obtenerFabrica("POSTRE").registrar("Brownie", 7000);

        assertInstanceOf(Bebida.class, bebida);
        assertInstanceOf(Plato.class, plato);
        assertInstanceOf(Postre.class, postre);
    }
}
