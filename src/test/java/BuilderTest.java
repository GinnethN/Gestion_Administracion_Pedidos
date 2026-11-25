import modelo.combos.Combo;
import modelo.combos.ComboBuilder;
import modelo.productos.Bebida;
import modelo.productos.Plato;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

class BuilderTest {
    @Test
    void deberiaConstruirComboCompletoYRechazarIncompleto() {
        Combo combo = new ComboBuilder()
                .plato(new Plato("Hamburguesa", 18000))
                .acompanamiento(new Plato("Papas", 6000))
                .bebida(new Bebida("Limonada", 5000))
                .construir();

        assertEquals(29000.0, combo.precio(), 0.01);

        ComboBuilder incompleto = new ComboBuilder().bebida(new Bebida("Limonada", 5000));
        assertThrows(IllegalStateException.class, incompleto::construir);
    }
}
