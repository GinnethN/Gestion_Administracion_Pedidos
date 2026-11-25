package modelo.menu;

import modelo.productos.Adicion;
import modelo.productos.ProductoMenu;

import java.util.*;

public class MenuConfigurable {
    private final Map<String, ProductoFactory> fabricas = new LinkedHashMap<>();
    private final List<ProductoMenu> menuItems = new ArrayList<>();
    private final List<Adicion> adicionesDisponibles = new ArrayList<>();

    public MenuConfigurable() {
        fabricas.put("BEBIDA", new BebidaFactory());
        fabricas.put("PLATO", new PlatoFactory());
        fabricas.put("POSTRE", new PostreFactory());
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        menuItems.add(obtenerFabrica("BEBIDA").registrar("Latte", 8000));
        menuItems.add(obtenerFabrica("BEBIDA").registrar("Café con Leche", 5000));
        menuItems.add(obtenerFabrica("BEBIDA").registrar("Jugo Natural", 6500));
        menuItems.add(obtenerFabrica("PLATO").registrar("Hamburguesa Artesanal", 18000));
        menuItems.add(obtenerFabrica("PLATO").registrar("Huevos con Arepa", 11000));
        menuItems.add(obtenerFabrica("PLATO").registrar("Bandeja del Día", 16000));
        menuItems.add(obtenerFabrica("PLATO").registrar("Papas Francesas", 6000));
        menuItems.add(obtenerFabrica("POSTRE").registrar("Brownie", 7000));
        menuItems.add(obtenerFabrica("POSTRE").registrar("Fruta", 6000));

        adicionesDisponibles.add(new Adicion("Leche de Almendras", 2500));
        adicionesDisponibles.add(new Adicion("Shot Extra", 3000));
        adicionesDisponibles.add(new Adicion("Queso Cheddar", 3500));
    }

    public ProductoFactory obtenerFabrica(String tipo) {
        ProductoFactory factory = fabricas.get(tipo.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException("Tipo de producto no soportado: " + tipo);
        }
        return factory;
    }

    public ProductoMenu registrarEnMenu(String tipo, String nombre, double precio) {
        ProductoMenu creado = obtenerFabrica(tipo).registrar(nombre, precio);
        menuItems.add(creado);
        return creado;
    }

    public List<ProductoMenu> cargarMenu() {
        return Collections.unmodifiableList(menuItems);
    }

    public List<Adicion> getAdicionesDisponibles() {
        return Collections.unmodifiableList(adicionesDisponibles);
    }
}
