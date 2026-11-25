package modelo.pedidos;

import modelo.productos.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final int id;
    private final String mesa;
    private EstadoPedido estado;
    private final List<ItemPedido> items = new ArrayList<>();
    private Pago pago;

    public Pedido(int id, String mesa) {
        this.id = id;
        this.mesa = mesa;
        this.estado = EstadoPedido.RECIBIDO;
    }

    public void agregarItem(Producto producto, int cantidad) {
        if (estado != EstadoPedido.RECIBIDO) {
            throw new IllegalStateException("Solo se pueden agregar ítems cuando el pedido está RECIBIDO.");
        }
        items.add(new ItemPedido(producto, cantidad));
    }

    public double calcularTotal() {
        return items.stream().mapToDouble(ItemPedido::subtotal).sum();
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        boolean permitido = switch (this.estado) {
            case RECIBIDO -> (nuevoEstado == EstadoPedido.EN_PREPARACION || nuevoEstado == EstadoPedido.CANCELADO);
            case EN_PREPARACION -> (nuevoEstado == EstadoPedido.LISTO || nuevoEstado == EstadoPedido.CANCELADO);
            case LISTO -> (nuevoEstado == EstadoPedido.ENTREGADO);
            case ENTREGADO -> (nuevoEstado == EstadoPedido.PAGADO);
            case PAGADO, CANCELADO -> false;
        };

        if (!permitido) {
            throw new IllegalStateException("Transición no permitida de " + this.estado + " a " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }

    public void registrarPago(String metodo) {
        if (this.estado != EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("El pedido debe estar ENTREGADO para registrarse el pago.");
        }
        this.pago = new Pago(metodo, calcularTotal());
        cambiarEstado(EstadoPedido.PAGADO);
    }

    public int getId() { return id; }
    public String getMesa() { return mesa; }
    public EstadoPedido getEstado() { return estado; }
    public List<ItemPedido> getItems() { return Collections.unmodifiableList(items); }
    public Pago getPago() { return pago; }
}
