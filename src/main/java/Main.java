import controlador.CajaController;
import controlador.MenuController;
import controlador.PedidoController;
import modelo.menu.MenuConfigurable;
import vista.Vista;
import vista.consola.VistaConsola;
import vista.grafica.VentanaPrincipal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MenuConfigurable menuConfigurable = new MenuConfigurable();
        MenuController menuController = new MenuController(menuConfigurable);
        PedidoController pedidoController = new PedidoController();
        CajaController cajaController = new CajaController(pedidoController);

        Vista vista;
        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) {
            vista = new VistaConsola(menuController, pedidoController, cajaController);
        } else if (args.length > 0 && args[0].equalsIgnoreCase("gui")) {
            vista = new VentanaPrincipal(menuController, pedidoController, cajaController);
        } else {
            System.out.println("1. Vista Consola");
            System.out.println("2. Vista Gráfica");
            System.out.print("Elija vista: ");
            String opcion = new Scanner(System.in).nextLine();
            vista = "1".equals(opcion)
                    ? new VistaConsola(menuController, pedidoController, cajaController)
                    : new VentanaPrincipal(menuController, pedidoController, cajaController);
        }

        vista.iniciar();
    }
}
