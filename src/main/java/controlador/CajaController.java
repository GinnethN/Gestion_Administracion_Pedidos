package controlador;

import modelo.pedidos.EstadoPedido;
import modelo.pedidos.Pedido;

public class CajaController {
    private final PedidoController pedidoController;

    public CajaController(PedidoController pedidoController) {
        this.pedidoController = pedidoController;
    }

    public void cobrarPedido(Pedido pedido, String metodo) {
        pedido.registrarPago(metodo);
    }

    public double calcularTotalVentas() {
        return pedidoController.listarPedidos().stream()
                .filter(p -> p.getEstado() == EstadoPedido.PAGADO)
                .mapToDouble(Pedido::calcularTotal)
                .sum();
    }
}
