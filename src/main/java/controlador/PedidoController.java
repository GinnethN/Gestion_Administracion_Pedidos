package controlador;

import modelo.combos.Combo;
import modelo.combos.ComboBuilder;
import modelo.pedidos.EstadoPedido;
import modelo.pedidos.Pedido;
import modelo.productos.Adicion;
import modelo.productos.Producto;
import modelo.productos.ProductoConAdicion;
import modelo.productos.ProductoMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoController {
    private final List<Pedido> pedidos = new ArrayList<>();
    private int consecutivo = 1;

    public Pedido crearPedido(String mesa) {
        Pedido pedido = new Pedido(consecutivo++, mesa);
        pedidos.add(pedido);
        return pedido;
    }

    public Producto aplicarAdiciones(Producto base, List<Adicion> adiciones) {
        Producto actual = base;
        for (Adicion adicion : adiciones) {
            actual = new ProductoConAdicion(actual, adicion);
        }
        return actual;
    }

    public Combo armarCombo(ProductoMenu plato, ProductoMenu acompanamiento, ProductoMenu bebida, ProductoMenu postre) {
        return new ComboBuilder()
                .plato(plato)
                .acompanamiento(acompanamiento)
                .bebida(bebida)
                .postre(postre)
                .construir();
    }

    public void agregarItem(Pedido pedido, Producto producto, int cantidad) {
        pedido.agregarItem(producto, cantidad);
    }

    public void cambiarEstado(Pedido pedido, EstadoPedido nuevoEstado) {
        pedido.cambiarEstado(nuevoEstado);
    }

    public List<Pedido> listarPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}
