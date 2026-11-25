package modelo.combos;

import modelo.productos.Producto;
import modelo.productos.ProductoMenu;

public class Combo implements Producto {
    private final ProductoMenu plato;
    private final ProductoMenu acompanamiento;
    private final ProductoMenu bebida;
    private final ProductoMenu postre;
    private final double recargos;

    Combo(ProductoMenu plato, ProductoMenu acompanamiento, ProductoMenu bebida, ProductoMenu postre, double recargos) {
        this.plato = plato;
        this.acompanamiento = acompanamiento;
        this.bebida = bebida;
        this.postre = postre;
        this.recargos = recargos;
    }

    @Override
    public double precio() {
        double total = plato.precio() + recargos;
        if (acompanamiento != null) total += acompanamiento.precio();
        if (bebida != null) total += bebida.precio();
        if (postre != null) total += postre.precio();
        return total;
    }

    @Override
    public String descripcion() {
        StringBuilder sb = new StringBuilder("Combo [Plato: ").append(plato.descripcion());
        if (acompanamiento != null) sb.append(", Acompañamiento: ").append(acompanamiento.descripcion());
        if (bebida != null) sb.append(", Bebida: ").append(bebida.descripcion());
        if (postre != null) sb.append(", Postre: ").append(postre.descripcion());
        if (recargos > 0) sb.append(", Recargos: $").append(recargos);
        sb.append("]");
        return sb.toString();
    }

    public ProductoMenu getPlato() { return plato; }
    public ProductoMenu getAcompanamiento() { return acompanamiento; }
    public ProductoMenu getBebida() { return bebida; }
    public ProductoMenu getPostre() { return postre; }
    public double getRecargos() { return recargos; }
}
