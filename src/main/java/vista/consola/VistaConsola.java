package vista.consola;

import controlador.CajaController;
import controlador.MenuController;
import controlador.PedidoController;
import modelo.combos.Combo;
import modelo.pedidos.EstadoPedido;
import modelo.pedidos.Pedido;
import modelo.productos.Adicion;
import modelo.productos.Producto;
import modelo.productos.ProductoMenu;
import vista.Vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VistaConsola implements Vista {
    private final MenuController menuController;
    private final PedidoController pedidoController;
    private final CajaController cajaController;
    private final Scanner scanner = new Scanner(System.in);

    public VistaConsola(MenuController menuController, PedidoController pedidoController, CajaController cajaController) {
        this.menuController = menuController;
        this.pedidoController = pedidoController;
        this.cajaController = cajaController;
    }

    @Override
    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== CAFETERÍA UNILIBRE - CONSOLA ===");
            System.out.println("1. Ver menú y adiciones");
            System.out.println("2. Registrar producto en menú (Factory Method)");
            System.out.println("3. Crear pedido con adiciones (Decorator)");
            System.out.println("4. Armar combo paso a paso (Builder)");
            System.out.println("5. Cambiar estado o cobrar pedido");
            System.out.println("6. Ver total de ventas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> mostrarMenu();
                    case 2 -> registrarProducto();
                    case 3 -> crearPedidoDecorado();
                    case 4 -> crearCombo();
                    case 5 -> gestionarPedido();
                    case 6 -> System.out.println("Total ventas pagadas: $" + cajaController.calcularTotalVentas());
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void mostrarMenu() {
        List<ProductoMenu> menu = menuController.cargarMenu();
        System.out.println("\n--- MENÚ ---");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(i + ". " + menu.get(i).descripcion() + " - $" + menu.get(i).precio());
        }
        List<Adicion> adiciones = menuController.obtenerAdiciones();
        System.out.println("\n--- ADICIONES ---");
        for (int i = 0; i < adiciones.size(); i++) {
            System.out.println(i + ". " + adiciones.get(i));
        }
    }

    private void registrarProducto() {
        System.out.print("Tipo (BEBIDA / PLATO / POSTRE): ");
        String tipo = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio base: ");
        double precio = Double.parseDouble(scanner.nextLine());
        ProductoMenu creado = menuController.registrarProducto(tipo, nombre, precio);
        System.out.println("Registrado: " + creado.descripcion() + " ($" + creado.precio() + ")");
    }

    private void crearPedidoDecorado() {
        mostrarMenu();
        System.out.print("Mesa: ");
        String mesa = scanner.nextLine();
        Pedido pedido = pedidoController.crearPedido(mesa);

        System.out.print("Índice producto base: ");
        Producto base = menuController.cargarMenu().get(Integer.parseInt(scanner.nextLine()));

        List<Adicion> elegidas = new ArrayList<>();
        while (true) {
            System.out.print("Índice adición (-1 para terminar): ");
            int idx = Integer.parseInt(scanner.nextLine());
            if (idx == -1) break;
            elegidas.add(menuController.obtenerAdiciones().get(idx));
        }

        Producto finalProd = pedidoController.aplicarAdiciones(base, elegidas);
        pedidoController.agregarItem(pedido, finalProd, 1);
        System.out.println("Pedido #" + pedido.getId() + ": " + finalProd.descripcion() + " | Total: $" + pedido.calcularTotal());
    }

    private void crearCombo() {
        mostrarMenu();
        List<ProductoMenu> menu = menuController.cargarMenu();
        System.out.print("Mesa: ");
        String mesa = scanner.nextLine();

        System.out.print("Índice plato principal (-1 omitir): ");
        int pIdx = Integer.parseInt(scanner.nextLine());
        ProductoMenu plato = pIdx >= 0 ? menu.get(pIdx) : null;

        System.out.print("Índice acompañamiento (-1 omitir): ");
        int aIdx = Integer.parseInt(scanner.nextLine());
        ProductoMenu acomp = aIdx >= 0 ? menu.get(aIdx) : null;

        System.out.print("Índice bebida (-1 omitir): ");
        int bIdx = Integer.parseInt(scanner.nextLine());
        ProductoMenu bebida = bIdx >= 0 ? menu.get(bIdx) : null;

        System.out.print("Índice postre (-1 omitir): ");
        int posIdx = Integer.parseInt(scanner.nextLine());
        ProductoMenu postre = posIdx >= 0 ? menu.get(posIdx) : null;

        Combo combo = pedidoController.armarCombo(plato, acomp, bebida, postre);
        Pedido pedido = pedidoController.crearPedido(mesa);
        pedidoController.agregarItem(pedido, combo, 1);
        System.out.println("Combo agregado: " + combo.descripcion() + " | Total: $" + pedido.calcularTotal());
    }

    private void gestionarPedido() {
        List<Pedido> pedidos = pedidoController.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }
        pedidos.forEach(p -> System.out.println("#" + p.getId() + " | Mesa: " + p.getMesa() + " | " + p.getEstado() + " | $" + p.calcularTotal()));
        System.out.print("ID pedido: ");
        int id = Integer.parseInt(scanner.nextLine());
        Pedido pedido = pedidos.stream().filter(p -> p.getId() == id).findFirst().orElseThrow();

        System.out.println("1. EN_PREPARACION | 2. LISTO | 3. ENTREGADO | 4. PAGADO");
        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1 -> pedidoController.cambiarEstado(pedido, EstadoPedido.EN_PREPARACION);
            case 2 -> pedidoController.cambiarEstado(pedido, EstadoPedido.LISTO);
            case 3 -> pedidoController.cambiarEstado(pedido, EstadoPedido.ENTREGADO);
            case 4 -> cajaController.cobrarPedido(pedido, "EFECTIVO");
        }
        System.out.println("Estado actual: " + pedido.getEstado());
    }
}
