import modelo.productos.Adicion;
import modelo.productos.Bebida;
import modelo.productos.Producto;
import modelo.productos.ProductoConAdicion;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

class DecoratorTest {
    @Test
    void deberiaSumarPrecioYActualizarDescripcionConAdiciones() {
        Producto latte = new Bebida("Latte", 8000);
        Producto decorado = new ProductoConAdicion(latte, new Adicion("Leche de Almendras", 2500));
        decorado = new ProductoConAdicion(decorado, new Adicion("Shot Extra", 3000));

        assertEquals(13500.0, decorado.precio(), 0.01);
        assertEquals("Latte + Leche de Almendras + Shot Extra", decorado.descripcion());
    }
}
