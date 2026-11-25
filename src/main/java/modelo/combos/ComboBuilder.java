package modelo.combos;

import modelo.productos.ProductoMenu;

public class ComboBuilder {
    private ProductoMenu plato;
    private ProductoMenu acompanamiento;
    private ProductoMenu bebida;
    private ProductoMenu postre;
    private double recargos;

    public ComboBuilder plato(ProductoMenu p) {
        this.plato = p;
        return this;
    }

    public ComboBuilder acompanamiento(ProductoMenu a) {
        this.acompanamiento = a;
        return this;
    }

    public ComboBuilder bebida(ProductoMenu b) {
        this.bebida = b;
        return this;
    }

    public ComboBuilder postre(ProductoMenu p) {
        this.postre = p;
        return this;
    }

    public Combo construir() {
        if (plato == null) {
            throw new IllegalStateException("Faltan piezas obligatorias: debe seleccionar un plato principal.");
        }
        if (acompanamiento == null && bebida == null) {
            throw new IllegalStateException("Faltan piezas obligatorias: debe seleccionar acompañamiento o bebida.");
        }
        this.recargos = 0.0;
        return new Combo(plato, acompanamiento, bebida, postre, recargos);
    }
}
