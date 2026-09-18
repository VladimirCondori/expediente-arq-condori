# C4 — Sistema de Inventarios

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

---

## Nivel 1 — Contexto

¿Quién usa el sistema y con qué otros sistemas habla?

```mermaid
flowchart TB
    almacenero["👤 Almacenero<br>(registra entradas y salidas)"]
    inventario["👤 Encargado de Inventario<br>(autoriza ajustes y fija el stock minimo)"]
    compras["👤 Encargado de Compras<br>(emite ordenes de compra)"]
    admin["👤 Administrador<br>(usuarios y permisos)"]

    sistema["📦 SISTEMA DE INVENTARIOS<br>Controla que hay, cuanto,<br>en que almacen y por que cambio"]

    pos["🛒 Sistema de Ventas / POS<br>(externo)"]
    notificaciones["📧 Servicio de notificaciones<br>(externo)"]
    proveedor["🏭 Proveedor<br>(externo)"]

    almacenero -->|"registra movimientos"| sistema
    inventario -->|"autoriza ajustes"| sistema
    compras -->|"emite ordenes de compra"| sistema
    admin -->|"gestiona usuarios y roles"| sistema

    pos -->|"consulta stock y pide la salida por venta"| sistema
    sistema -->|"avisa stock bajo"| notificaciones
    sistema -->|"envia la orden de compra"| proveedor
```

---

## Nivel 2 — Contenedores

¿De que piezas ejecutables y almacenes esta hecho el sistema?

```mermaid
flowchart TB
    almacenero["👤 Almacenero"]
    inventario["👤 Encargado de Inventario"]
    compras["👤 Encargado de Compras"]

    subgraph sistema["📦 SISTEMA DE INVENTARIOS"]
        app["📱 App de almacen<br>Kotlin / Android<br>Pantallas de movimientos, stock y conteo"]
        api["⚙️ Logica de negocio<br>Kotlin<br>Movimientos, existencias, compras<br>(aca viven SOLID y los patrones)"]
        bd[("🗄️ Base de datos<br>SQL<br>Productos, existencias, kardex")]
        avisos["🛎️ Servicio de avisos<br>Kotlin<br>Observer: publica stock-bajo<br>a los suscriptores"]
    end

    pos["🛒 Sistema de Ventas / POS (externo)"]
    notificaciones["📧 Servicio de notificaciones (externo)"]

    almacenero --> app
    inventario --> app
    compras --> app
    app --> api
    pos -->|"pide salida por venta"| api
    api --> bd
    api -->|"publica evento stock-bajo"| avisos
    avisos --> notificaciones
```

