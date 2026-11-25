package controlador;

import modelo.menu.MenuConfigurable;
import modelo.productos.Adicion;
import modelo.productos.ProductoMenu;

import java.util.List;

public class MenuController {
    private final MenuConfigurable menuConfigurable;

    public MenuController(MenuConfigurable menuConfigurable) {
        this.menuConfigurable = menuConfigurable;
    }

    public List<ProductoMenu> cargarMenu() {
        return menuConfigurable.cargarMenu();
    }

    public List<Adicion> obtenerAdiciones() {
        return menuConfigurable.getAdicionesDisponibles();
    }

    public ProductoMenu registrarProducto(String tipo, String nombre, double precio) {
        return menuConfigurable.registrarEnMenu(tipo, nombre, precio);
    }
}
