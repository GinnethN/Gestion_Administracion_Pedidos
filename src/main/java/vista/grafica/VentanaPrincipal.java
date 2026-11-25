package vista.grafica;

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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame implements Vista {
    private final MenuController menuController;
    private final PedidoController pedidoController;
    private final CajaController cajaController;

    // Paleta de colores estilo Cafetería Moderna
    private static final Color COLOR_ESPRESSO = new Color(54, 34, 24);
    private static final Color COLOR_CAFE_MEDIO = new Color(111, 78, 55);
    private static final Color COLOR_CREMA_FONDO = new Color(248, 244, 239);
    private static final Color COLOR_TARJETA = Color.WHITE;
    private static final Color COLOR_ACENTO = new Color(198, 124, 78);
    private static final Color COLOR_VERDE = new Color(46, 125, 50);
    private static final Color COLOR_BORDE = new Color(224, 214, 204);

    // Componentes dinámicos
    private JTextField txtMesa;
    private JComboBox<String> comboProductos;
    private JSpinner spinnerCantidad;
    private List<JCheckBox> checksAdiciones;
    private JLabel lblVistaPreviaPrecio;

    // Componentes Combo (Builder)
    private JComboBox<String> cbComboPlato;
    private JComboBox<String> cbComboAcomp;
    private JComboBox<String> cbComboBebida;
    private JComboBox<String> cbComboPostre;

    // Componentes Menú (Factory Method)
    private JComboBox<String> cbNuevoTipo;
    private JTextField txtNuevoNombre;
    private JTextField txtNuevoPrecio;

    // Tabla de pedidos y métricas
    private DefaultTableModel modeloTablaPedidos;
    private JTable tablaPedidos;
    private JTextArea areaBitacora;
    private JLabel lblMetricasPedidos;
    private JLabel lblMetricasVentas;

    public VentanaPrincipal(MenuController menuController, PedidoController pedidoController, CajaController cajaController) {
        this.menuController = menuController;
        this.pedidoController = pedidoController;
        this.cajaController = cajaController;
        aplicarEstiloGlobal();
        inicializarUI();
    }

    private void aplicarEstiloGlobal() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private void inicializarUI() {
        setTitle("Cafetería UniLibre — Sistema de Gestión de Pedidos");
        setSize(1060, 680);
        setMinimumSize(new Dimension(960, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_CREMA_FONDO);

        // 1. ENCABEZADO SUPERIOR
        add(crearHeader(), BorderLayout.NORTH);

        // 2. CONTENIDO CENTRAL DIVIDIDO (Izquierda: Operación con Pestañas | Derecha: Pedidos y Bitácora)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelOperaciones(), crearPanelSeguimiento());
        splitPane.setDividerLocation(450);
        splitPane.setDividerSize(6);
        splitPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        splitPane.setBackground(COLOR_CREMA_FONDO);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_ESPRESSO);
        header.setBorder(new EmptyBorder(14, 22, 14, 22));

        JPanel titulos = new JPanel(new GridLayout(2, 1));
        titulos.setOpaque(false);

        JLabel lblTitulo = new JLabel("☕ CAFETERÍA UNILIBRE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Gestión de Pedidos, Personalización y Caja  |  Decorator • Builder • Factory Method");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(225, 210, 195));

        titulos.add(lblTitulo);
        titulos.add(lblSubtitulo);

        // Tarjetas de resumen en el header
        JPanel panelKPIs = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelKPIs.setOpaque(false);

        lblMetricasPedidos = crearBadgeKPI("Pedidos Activos: 0", COLOR_CAFE_MEDIO);
        lblMetricasVentas = crearBadgeKPI("Recaudado: $0", COLOR_VERDE);

        panelKPIs.add(lblMetricasPedidos);
        panelKPIs.add(lblMetricasVentas);

        header.add(titulos, BorderLayout.WEST);
        header.add(panelKPIs, BorderLayout.EAST);
        return header;
    }

    private JLabel crearBadgeKPI(String texto, Color bg) {
        JLabel badge = new JLabel(texto);
        badge.setOpaque(true);
        badge.setBackground(bg);
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 13));
        badge.setBorder(new EmptyBorder(8, 14, 8, 14));
        return badge;
    }

    private JTabbedPane crearPanelOperaciones() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(COLOR_TARJETA);

        tabs.addTab("1. Pedido + Adiciones", crearTabDecorator());
        tabs.addTab("2. Armar Combo", crearTabBuilder());
        tabs.addTab("3. Menú (Factory)", crearTabFactory());
        return tabs;
    }

    // PESTAÑA 1: PATRÓN DECORATOR
    private JPanel crearTabDecorator() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TARJETA);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
        form.setOpaque(false);

        txtMesa = estilarTextField(new JTextField("Mesa 1"));
        comboProductos = new JComboBox<>();
        comboProductos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboProductos.addActionListener(e -> actualizarVistaPreviaPrecio());

        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spinnerCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spinnerCantidad.addChangeListener(e -> actualizarVistaPreviaPrecio());

        form.add(crearEtiquetaCampo("Mesa o Cliente:"));
        form.add(txtMesa);
        form.add(crearEtiquetaCampo("Producto Base del Menú:"));
        form.add(comboProductos);
        form.add(crearEtiquetaCampo("Cantidad:"));
        form.add(spinnerCantidad);

        // Panel de Checkboxes para Adiciones (Decorator)
        JPanel panelAdiciones = new JPanel(new GridLayout(0, 1, 4, 4));
        panelAdiciones.setBackground(new Color(252, 249, 245));
        panelAdiciones.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(COLOR_BORDE), "Adiciones Opcionales (Patrón Decorator)",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), COLOR_CAFE_MEDIO));

        checksAdiciones = new ArrayList<>();
        for (Adicion ad : menuController.obtenerAdiciones()) {
            JCheckBox chk = new JCheckBox(ad.getNombre() + "  (+$" + String.format("%,.0f", ad.getPrecio()) + ")");
            chk.setOpaque(false);
            chk.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            chk.addActionListener(e -> actualizarVistaPreviaPrecio());
            checksAdiciones.add(chk);
            panelAdiciones.add(chk);
        }

        // Pie de tarjeta con precio estimado y botón principal
        JPanel footer = new JPanel(new BorderLayout(8, 8));
        footer.setOpaque(false);

        lblVistaPreviaPrecio = new JLabel("Total estimado: $0", SwingConstants.RIGHT);
        lblVistaPreviaPrecio.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblVistaPreviaPrecio.setForeground(COLOR_ESPRESSO);

        JButton btnCrear = crearBotonPrincipal("Registrar Pedido Personalizado", COLOR_ACENTO);
        btnCrear.addActionListener(e -> accionCrearPedidoDecorado());

        footer.add(lblVistaPreviaPrecio, BorderLayout.NORTH);
        footer.add(btnCrear, BorderLayout.SOUTH);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(panelAdiciones), BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        refrescarCombosProductos();
        return panel;
    }

    // PESTAÑA 2: PATRÓN BUILDER
    private JPanel crearTabBuilder() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TARJETA);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel info = new JLabel("<html><b>Construcción por pasos (Patrón Builder):</b><br>" +
                "Selecciona el plato principal (obligatorio) y al menos un complemento válido.</html>");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(COLOR_CAFE_MEDIO);

        JPanel pasos = new JPanel(new GridLayout(0, 1, 6, 6));
        pasos.setOpaque(false);

        cbComboPlato = new JComboBox<>();
        cbComboAcomp = new JComboBox<>();
        cbComboBebida = new JComboBox<>();
        cbComboPostre = new JComboBox<>();

        pasos.add(crearEtiquetaCampo("Paso 1: Plato Principal (Obligatorio) *"));
        pasos.add(cbComboPlato);
        pasos.add(crearEtiquetaCampo("Paso 2: Acompañamiento (Opcional)"));
        pasos.add(cbComboAcomp);
        pasos.add(crearEtiquetaCampo("Paso 3: Bebida (Opcional)"));
        pasos.add(cbComboBebida);
        pasos.add(crearEtiquetaCampo("Paso 4: Postre (Opcional)"));
        pasos.add(cbComboPostre);

        JButton btnConstruirCombo = crearBotonPrincipal("Construir y Agregar Combo al Pedido", COLOR_CAFE_MEDIO);
        btnConstruirCombo.addActionListener(e -> accionConstruirCombo());

        panel.add(info, BorderLayout.NORTH);
        panel.add(pasos, BorderLayout.CENTER);
        panel.add(btnConstruirCombo, BorderLayout.SOUTH);

        refrescarCombosBuilder();
        return panel;
    }

    // PESTAÑA 3: PATRÓN FACTORY METHOD
    private JPanel crearTabFactory() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TARJETA);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel info = new JLabel("<html><b>Creación mediante Fábricas (Patrón Factory Method):</b><br>" +
                "El controlador delega la instanciación a <i>BebidaFactory</i>, <i>PlatoFactory</i> o <i>PostreFactory</i>.</html>");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(COLOR_CAFE_MEDIO);

        JPanel campos = new JPanel(new GridLayout(0, 1, 8, 8));
        campos.setOpaque(false);

        cbNuevoTipo = new JComboBox<>(new String[]{"BEBIDA", "PLATO", "POSTRE"});
        txtNuevoNombre = estilarTextField(new JTextField());
        txtNuevoPrecio = estilarTextField(new JTextField());

        campos.add(crearEtiquetaCampo("Categoría / Fábrica a utilizar:"));
        campos.add(cbNuevoTipo);
        campos.add(crearEtiquetaCampo("Nombre del nuevo producto:"));
        campos.add(txtNuevoNombre);
        campos.add(crearEtiquetaCampo("Precio base ($):"));
        campos.add(txtNuevoPrecio);

        JButton btnRegistrarProd = crearBotonPrincipal("Registrar en Catálogo (Factory Method)", COLOR_ESPRESSO);
        btnRegistrarProd.addActionListener(e -> accionRegistrarProductoFactory());

        panel.add(info, BorderLayout.NORTH);
        panel.add(campos, BorderLayout.CENTER);
        panel.add(btnRegistrarProd, BorderLayout.SOUTH);
        return panel;
    }

    // PANEL DERECHO: TABLA DE PEDIDOS, FLUJO DE ESTADOS Y BITÁCORA
    private JPanel crearPanelSeguimiento() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE), new EmptyBorder(12, 12, 12, 12)));

        JLabel lblSec = new JLabel("📋 Pedidos en Curso y Control de Estados");
        lblSec.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSec.setForeground(COLOR_ESPRESSO);

        // Tabla estilizada
        String[] columnas = {"ID", "Mesa", "Detalle del Pedido", "Estado", "Total"};
        modeloTablaPedidos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPedidos = new JTable(modeloTablaPedidos);
        tablaPedidos.setRowHeight(28);
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaPedidos.getTableHeader().setBackground(COLOR_ESPRESSO);
        tablaPedidos.getTableHeader().setForeground(Color.WHITE);
        tablaPedidos.getColumnModel().getColumn(0).setMaxWidth(45);
        tablaPedidos.getColumnModel().getColumn(1).setMaxWidth(85);
        tablaPedidos.getColumnModel().getColumn(3).setPreferredWidth(115);
        tablaPedidos.getColumnModel().getColumn(4).setMaxWidth(95);

        // Botonera de estados
        JPanel barraEstados = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        barraEstados.setBackground(new Color(245, 240, 235));

        JButton btnPrep = crearBotonEstado("1. Preparar", new Color(230, 126, 34));
        JButton btnListo = crearBotonEstado("2. Listo", new Color(41, 128, 185));
        JButton btnEntregar = crearBotonEstado("3. Entregar", new Color(142, 68, 173));
        JButton btnCobrar = crearBotonEstado("4. Cobrar", COLOR_VERDE);
        JButton btnCancelar = crearBotonEstado("Cancelar", new Color(192, 57, 43));

        btnPrep.addActionListener(e -> cambiarEstadoTabla(EstadoPedido.EN_PREPARACION));
        btnListo.addActionListener(e -> cambiarEstadoTabla(EstadoPedido.LISTO));
        btnEntregar.addActionListener(e -> cambiarEstadoTabla(EstadoPedido.ENTREGADO));
        btnCobrar.addActionListener(e -> cobrarTabla());
        btnCancelar.addActionListener(e -> cambiarEstadoTabla(EstadoPedido.CANCELADO));

        barraEstados.add(btnPrep);
        barraEstados.add(btnListo);
        barraEstados.add(btnEntregar);
        barraEstados.add(btnCobrar);
        barraEstados.add(btnCancelar);

        // Consola de eventos (Bitácora visual)
        areaBitacora = new JTextArea(6, 20);
        areaBitacora.setEditable(false);
        areaBitacora.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaBitacora.setBackground(new Color(38, 34, 32));
        areaBitacora.setForeground(new Color(235, 225, 215));
        areaBitacora.setBorder(new EmptyBorder(8, 8, 8, 8));
        areaBitacora.setText("Sistema iniciado. Listo para recibir pedidos...\n");

        JPanel centroTabla = new JPanel(new BorderLayout(0, 6));
        centroTabla.setOpaque(false);
        centroTabla.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
        centroTabla.add(barraEstados, BorderLayout.SOUTH);

        panel.add(lblSec, BorderLayout.NORTH);
        panel.add(centroTabla, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaBitacora), BorderLayout.SOUTH);

        return panel;
    }

    // --- LÓGICA DE ACCIONES CONECTADAS AL CONTROLADOR ---

    private void refrescarCombosProductos() {
        comboProductos.removeAllItems();
        for (ProductoMenu p : menuController.cargarMenu()) {
            comboProductos.addItem(p.descripcion() + " — $" + String.format("%,.0f", p.precio()));
        }
        actualizarVistaPreviaPrecio();
    }

    private void refrescarCombosBuilder() {
        List<ProductoMenu> menu = menuController.cargarMenu();
        cbComboPlato.removeAllItems();
        cbComboAcomp.removeAllItems();
        cbComboBebida.removeAllItems();
        cbComboPostre.removeAllItems();

        cbComboPlato.addItem("-- Seleccione Plato --");
        cbComboAcomp.addItem("-- Ninguno --");
        cbComboBebida.addItem("-- Ninguna --");
        cbComboPostre.addItem("-- Ninguno --");

        for (ProductoMenu p : menu) {
            String item = p.descripcion() + " ($" + String.format("%,.0f", p.precio()) + ")";
            cbComboPlato.addItem(item);
            cbComboAcomp.addItem(item);
            cbComboBebida.addItem(item);
            cbComboPostre.addItem(item);
        }
    }

    private void actualizarVistaPreviaPrecio() {
        int idx = comboProductos.getSelectedIndex();
        if (idx < 0 || menuController.cargarMenu().isEmpty()) return;

        double total = menuController.cargarMenu().get(idx).precio();
        List<Adicion> disponibles = menuController.obtenerAdiciones();
        for (int i = 0; i < checksAdiciones.size(); i++) {
            if (checksAdiciones.get(i).isSelected()) {
                total += disponibles.get(i).getPrecio();
            }
        }
        int cantidad = (Integer) spinnerCantidad.getValue();
        lblVistaPreviaPrecio.setText("Total estimado: $" + String.format("%,.0f", total * cantidad));
    }

    private void accionCrearPedidoDecorado() {
        try {
            int idx = comboProductos.getSelectedIndex();
            Producto base = menuController.cargarMenu().get(idx);

            List<Adicion> elegidas = new ArrayList<>();
            List<Adicion> disponibles = menuController.obtenerAdiciones();
            for (int i = 0; i < checksAdiciones.size(); i++) {
                if (checksAdiciones.get(i).isSelected()) {
                    elegidas.add(disponibles.get(i));
                }
            }

            Producto decorado = pedidoController.aplicarAdiciones(base, elegidas);
            int cantidad = (Integer) spinnerCantidad.getValue();

            Pedido pedido = pedidoController.crearPedido(txtMesa.getText().trim());
            pedidoController.agregarItem(pedido, decorado, cantidad);

            // Limpiar selección de checkboxes
            checksAdiciones.forEach(c -> c.setSelected(false));
            spinnerCantidad.setValue(1);
            actualizarVistaPreviaPrecio();

            actualizarTablaYMetricas();
            registrarLog("[Decorator] Pedido #" + pedido.getId() + " (" + pedido.getMesa() + "): " +
                    cantidad + "x " + decorado.descripcion() + " -> $" + String.format("%,.0f", pedido.calcularTotal()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en Pedido", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionConstruirCombo() {
        try {
            List<ProductoMenu> menu = menuController.cargarMenu();
            ProductoMenu plato = cbComboPlato.getSelectedIndex() > 0 ? menu.get(cbComboPlato.getSelectedIndex() - 1) : null;
            ProductoMenu acomp = cbComboAcomp.getSelectedIndex() > 0 ? menu.get(cbComboAcomp.getSelectedIndex() - 1) : null;
            ProductoMenu bebida = cbComboBebida.getSelectedIndex() > 0 ? menu.get(cbComboBebida.getSelectedIndex() - 1) : null;
            ProductoMenu postre = cbComboPostre.getSelectedIndex() > 0 ? menu.get(cbComboPostre.getSelectedIndex() - 1) : null;

            Combo combo = pedidoController.armarCombo(plato, acomp, bebida, postre);
            Pedido pedido = pedidoController.crearPedido(txtMesa.getText().trim());
            pedidoController.agregarItem(pedido, combo, 1);

            actualizarTablaYMetricas();
            registrarLog("[Builder] Combo creado en Pedido #" + pedido.getId() + ": " + combo.descripcion());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación ComboBuilder", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void accionRegistrarProductoFactory() {
        try {
            String tipo = (String) cbNuevoTipo.getSelectedItem();
            String nombre = txtNuevoNombre.getText().trim();
            double precio = Double.parseDouble(txtNuevoPrecio.getText().trim());

            ProductoMenu creado = menuController.registrarProducto(tipo, nombre, precio);
            txtNuevoNombre.setText("");
            txtNuevoPrecio.setText("");

            refrescarCombosProductos();
            refrescarCombosBuilder();
            registrarLog("[Factory Method] Nuevo " + tipo + " registrado: " + creado.descripcion() + " ($" + String.format("%,.0f", creado.precio()) + ")");
            JOptionPane.showMessageDialog(this, "Producto creado exitosamente mediante " + tipo + "Factory.");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio numérico válido.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Factory", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarEstadoTabla(EstadoPedido nuevoEstado) {
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido en la tabla primero.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Pedido p = pedidoController.listarPedidos().get(fila);
            pedidoController.cambiarEstado(p, nuevoEstado);
            actualizarTablaYMetricas();
            registrarLog("[Estado] Pedido #" + p.getId() + " -> " + p.getEstado());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Transición no permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cobrarTabla() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido entregado en la tabla para cobrar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Pedido p = pedidoController.listarPedidos().get(fila);
            String[] metodos = {"EFECTIVO", "TARJETA", "TRANSFERENCIA"};
            String metodo = (String) JOptionPane.showInputDialog(this,
                    "Total a cobrar: $" + String.format("%,.0f", p.calcularTotal()) + "\nSeleccione método de pago:",
                    "Registro de Pago", JOptionPane.QUESTION_MESSAGE, null, metodos, metodos[0]);
            if (metodo == null) return;

            cajaController.cobrarPedido(p, metodo);
            actualizarTablaYMetricas();
            registrarLog("[Caja] Pedido #" + p.getId() + " PAGADO (" + metodo + ") por $" + String.format("%,.0f", p.calcularTotal()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al cobrar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarTablaYMetricas() {
        modeloTablaPedidos.setRowCount(0);
        List<Pedido> todos = pedidoController.listarPedidos();
        for (Pedido p : todos) {
            String detalle = p.getItems().isEmpty() ? "-" :
                    p.getItems().get(0).getCantidad() + "x " + p.getItems().get(0).getProducto().descripcion();
            modeloTablaPedidos.addRow(new Object[]{
                    "#" + p.getId(),
                    p.getMesa(),
                    detalle,
                    p.getEstado(),
                    "$" + String.format("%,.0f", p.calcularTotal())
            });
        }
        lblMetricasPedidos.setText("Pedidos Totales: " + todos.size());
        lblMetricasVentas.setText("Recaudado: $" + String.format("%,.0f", cajaController.calcularTotalVentas()));
    }

    private void registrarLog(String mensaje) {
        areaBitacora.append(mensaje + "\n");
        areaBitacora.setCaretPosition(areaBitacora.getDocument().getLength());
    }

    // --- HELPERS VISUALES ---
    private JLabel crearEtiquetaCampo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(COLOR_ESPRESSO);
        return lbl;
    }

    private JTextField estilarTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE), new EmptyBorder(5, 8, 5, 8)));
        return field;
    }

    private JButton crearBotonPrincipal(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 38));
        return btn;
    }

    private JButton crearBotonEstado(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void iniciar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
