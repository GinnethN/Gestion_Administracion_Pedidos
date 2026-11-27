# Gestion_Administracion_Pedidos
Sistema de gestión de pedidos y administración para una cafetería 

Proyecto de Ingeniería de Software II: aplicación en Java para gestionar el menú, los pedidos y la caja de una cafetería, construida aplicando patrones de diseño.

## Funcionalidades implementadas:

- Gestión del menú configurable: crear, modificar, consultar y eliminar productos, categorías y adiciones
- Personalización de productos con adiciones que modifican precio y descripción (Decorator)
- Combos armados paso a paso, con validación de piezas obligatorias y cálculo de recargos (Builder)
- Creación de productos por categoría (bebida, plato, postre) desde el menú configurable (Factory Method)
- Registro de pedidos con cantidades, adiciones y cálculo del total
- Control de estados del pedido con transiciones válidas
- Promociones por horario o por día
- Pagos y cierre de caja
- Reportes de ventas
- Gestión de usuarios y roles

## Requisitos previos:

- JDK 21 (o JDK 17 o superior)
- Apache Maven
- Git

## Cómo obtener el proyecto:

```bash
git clone (https://github.com/GinnethN/Gestion_Administracion_Pedidos.git)
cd Gestion_Administracion_Pedidos

## Cómo compilar:
mvn clean compile

## Cómo ejecutar:
## Consola:
mvn exec:java -Dexec.mainClass="Main" -Dexec.args="consola"
## Interfaz gráfica:
mvn exec:java -Dexec.mainClass="Main" -Dexec.args="grafica"

## Cómo ejecutar pruebas unitarias:
mvn test

## Patrones de diseño aplicados:

| Patrón | Tipo | Problema que resuelve | Clases principales | Ejemplo en la cafetería |
| :--- | :--- | :--- | :--- | :--- |
| **Decorator** | Estructural | Agregar o repetir adiciones sin crear una clase por combinación, actualizando precio y descripción | `Producto`, `ProductoMenu`, `ProductoDecorator`, `ProductoConAdicion` | Un `Latte` ($8.000) con `Leche de Almendras` (+$2.500) y `Shot Extra` (+$3.000) |
| **Builder** | Creacional | Armar combos paso a paso validando piezas obligatorias y recargos | `Combo`, `ComboBuilder` | Armar un combo eligiendo plato obligatorio, acompañamiento, bebida y postre opcional |
| **Factory Method** | Creacional | Crear productos según su categoría sin acoplar el sistema a clases concretas | `ProductoFactory`, `BebidaFactory`, `PlatoFactory`, `PostreFactory` | Registrar bebidas, platos o postres en `MenuConfigurable` mediante su fábrica correspondiente |


## Arquitectura:
El sistema usa MVC para que la consola y la interfaz gráfica compartan el mismo núcleo.

Gestion_Administracion_Pedidos/
├── src/
│   ├── main/java/
│   │   ├── controlador/         CajaController, MenuController, PedidoController
│   │   ├── modelo/
│   │   │   ├── caja/            Gestión de caja
│   │   │   ├── combos/          Combo, ComboBuilder
│   │   │   ├── menu/            MenuConfigurable y fábricas (ProductoFactory, BebidaFactory, PlatoFactory, PostreFactory)
│   │   │   ├── pedidos/         Pedidos e ítems
│   │   │   ├── productos/       Producto, ProductoMenu, adiciones (Decorator) y categorías
│   │   │   ├── promociones/     Promociones
│   │   │   └── usuarios/        Usuarios y roles
│   │   ├── persistencia/        PedidoRepository
│   │   ├── vista/
│   │   │   ├── consola/         Vista por consola
│   │   │   ├── grafica/         Vista gráfica (Java Swing)
│   │   │   └── Vista            Interfaz común de vista
│   │   └── Main                 Crea el núcleo y elige la vista
│   └── test/java/               Pruebas unitarias con JUnit / Maven
└── pom.xml                      Configuración de Maven


## Documentación:

Documento de diseño y patrones: [Gestion_Administracion_Pedidos/Documentacion/Documento_Diseño_ProyectoIngenieríaII.pdf]
Plan del proyecto (alcance, requisitos, problemas de diseño):[Gestion_Administracion_Pedidos/Documentacion/ProyectoIngenieriaSoftwareII.pdf]
Diagramas UML (casos de uso, clases, secuencia): [Gestion_Administracion_Pedidos/UML/...]


## Uso herramientas apoyo: Inteligencia artificial:

Se usó asistencia de inteligencia artificial como apoyo para revisar la coherencia del diseño y del código, generar diagramas ilustrativos de apoyo y redactar borradores de secciones de la documentación y de este README.md. El diseño, la elección de los patrones y las decisiones del proyecto son de la autora, quien revisó, adaptó y puede explicar todo el contenido y el código.





