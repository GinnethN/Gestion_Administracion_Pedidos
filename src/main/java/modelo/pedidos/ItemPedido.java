package modelo.pedidos;

import modelo.productos.Producto;

public class ItemPedido {
    private final Producto producto;
    private final int cantidad;

    public ItemPedido(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {
            throw new IllegalArgumentException("Producto o cantidad inválida");
        }
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double subtotal() {
        return producto.precio() * cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
}
