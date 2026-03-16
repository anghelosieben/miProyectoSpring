# Modelo Entidad-Relación y Flujo de Datos

## 📊 Diagrama de Entidades

```
┌─────────────────┐       ┌─────────────────┐
│     Almacen     │       │    Categoria   │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)        │
│ nombre          │       │ nombre         │
│ ubicacion       │       └─────────────────┘
└────────┬────────┘                │
         │                           │
         │ 1:N                      │ 1:N
         ▼                           ▼
┌─────────────────────────────────────────────┐
│              StockAlmacen                    │
├─────────────────────────────────────────────┤
│ id (PK)                                    │
│ cantidad_actual                            │
│ id_producto (FK) ────────► Producto        │
│ id_almacen (FK) ─────────► Almacen         │
└─────────────────────────────────────────────┘

┌─────────────────┐       ┌─────────────────┐
│    Producto     │       │    Proveedor    │
├─────────────────┤       ├─────────────────┤
│ id (PK)        │       │ id (PK)        │
│ codigo         │       │ nombre         │
│ nombre         │       │ contacto       │
│ detalle        │       │ telefono       │
│ precio_compra  │       │ direccion      │
│ precio_venta   │       └─────────────────┘
│ imagen         │
│ stock          │
│ id_categoria   │
│ (FK)           │
└────────┬────────┘
         │
         │ 1:N
         ▼
┌─────────────────────────────────────────────┐
│           MovimientoInventario               │
├─────────────────────────────────────────────┤
│ id (PK)                                    │
│ tipo_movimiento (COMPRA/VENTA)             │
│ cantidad                                   │
│ documento_referencia                        │
│ observacion                                │
│ id_producto (FK) ────────► Producto       │
│ id_almacen (FK) ─────────► Almacen        │
└─────────────────────────────────────────────┘

┌─────────────────┐       ┌─────────────────┐
│      User       │       │      Role       │
├─────────────────┤       ├─────────────────┤
│ id (PK)        │       │ id (PK)        │
│ username       │       │ name            │
│ email          │       │ description     │
│ password       │       └────────┬────────┘
│ nombre         │                │
│ apellido       │                │ N:M
│ telefono       │                ▼
│ almacen_id(FK) │◄──────┌─────────────────┐
│                │       │    UserRoles    │
└────────┬───────┘       └────────┬────────┘
         │                        │
         │ N:M                    │
         ▼                        ▼
┌─────────────────────────────────────────────┐
│              Compra                         │
├─────────────────────────────────────────────┤
│ id (PK)                                    │
│ numero_factura                             │
│ subtotal                                   │
│ impuesto                                   │
│ descuento                                  │
│ total                                      │
│ forma_pago                                 │
│ observaciones                              │
│ id_proveedor (FK) ────────► Proveedor     │
│ id_almacen (FK) ─────────► Almacen        │
└────────┬──────────────────────────────────┘
         │
         │ 1:N
         ▼
┌─────────────────────────────────────────────┐
│           DetalleCompra                    │
├─────────────────────────────────────────────┤
│ id (PK)                                    │
│ cantidad                                   │
│ precio_unitario                            │
│ subtotal                                   │
│ id_compra (FK) ──────────► Compra        │
│ id_producto (FK) ────────► Producto      │
└─────────────────────────────────────────────┘

┌─────────────────┐       ┌─────────────────┐
│     Cliente     │       │     Venta       │
├─────────────────┤       ├─────────────────┤
│ id (PK)        │       │ id (PK)        │
│ nombre         │       │ numero_factura  │
│ apellido       │       │ subtotal        │
│ nit            │       │ impuesto        │
│ telefono       │       │ descuento       │
│ direccion      │       │ total           │
│ email          │       │ forma_pago      │
│ observacion    │       │ observaciones   │
└─────────────────┘       │ id_cliente(FK) │
                          │ id_almacen(FK) │
                          └────────┬────────┘
                                   │
                                   │ 1:N
                                   ▼
┌─────────────────────────────────────────────┐
│           DetalleVenta                      │
├─────────────────────────────────────────────┤
│ id (PK)                                    │
│ cantidad                                   │
│ precio_unitario                            │
│ subtotal                                   │
│ descuento_item                             │
│ id_venta (FK) ───────────► Venta          │
│ id_producto (FK) ────────► Producto      │
└─────────────────────────────────────────────┘
```

---

## 📋 Tablas y Relaciones

| Tabla | Tipo | Descripción |
|-------|------|-------------|
| `users` | Entidad | Usuarios del sistema |
| `roles` | Entidad | Roles (ADMIN, USER) |
| `user_roles` | Relación N:M | Relación users-roles |
| `almacenes` | Entidad | Locales/almacenes |
| `categorias` | Entidad | Categorías de productos |
| `productos` | Entidad | Catálogo de productos |
| `stock_almacen` | Entidad | Stock por almacén |
| `proveedores` | Entidad | Proveedores |
| `compras` | Entidad | Compras a proveedores |
| `detalle_compras` | Entidad | Items de compra |
| `clientes` | Entidad | Clientes |
| `ventas` | Entidad | Ventas a clientes |
| `detalle_ventas` | Entidad | Items de venta |
| `movimientos_inventario` | Entidad | Historial de movimientos |
| `permissions` | Entidad | Permisos |
| `role_permissions` | Relación N:M | Relación roles-permisos |

---

## 🔄 Flujos de Datos

### 1. Flujo de Compra (Entrada de Mercadería)

```
1. Usuario se loguea
   └─► Se obtiene su almacén asignado

2. Registrar Compra
   └─► POST /api/compras/agregar
       ├── Se guarda en tabla "compras"
       ├── Se guarda detalle en "detalle_compras"
       ├── StockAlmacen: cantidad_actual + cantidad_comprada
       │   └── (producto + almacén específico)
       └── MovimientoInventario: tipo = "COMPRA"
           └── Registra entrada de inventario
```

### 2. Flujo de Venta (Salida de Mercadería)

```
1. Usuario se loguea
   └─► Se obtiene su almacén asignado

2. Registrar Venta
   ├─► Validar stock en StockAlmacen
   │   └── (producto + almacén específico)
   ├─► Se guarda en tabla "ventas"
   ├─► Se guarda detalle en "detalle_ventas"
   ├─► StockAlmacen: cantidad_actual - cantidad_vendida
   │   └── (producto + almacén específico)
   └─► MovimientoInventario: tipo = "VENTA"
       └── Registra salida de inventario
```

### 3. Flujo de Usuario y Seguridad

```
1. Registro de Usuario
   └─► POST /api/auth/register
       ├── Se guarda en tabla "users"
       ├── Se asigna rol (USER por defecto)
       ├── Se asigna almacén (opcional)
       └── Se hashea la contraseña con BCrypt

2. Login
   └─► POST /api/auth/login
       ├── Valida credenciales
       └── Retorna JWT token con:
           ├── username
           ├── roles
           └── datos del almacén

3. Peticiones autenticadas
   └─► Header: Authorization: Bearer <token>
       └── Spring Security valida el token
           └── SecurityContextHolder tiene el usuario
```

---

## 🔗 Claves Foráneas (Foreign Keys)

| Tabla | Campo | Referencia |
|-------|-------|------------|
| users | almacen_id | almacenes.id |
| user_roles | user_id | users.id |
| user_roles | role_id | roles.id |
| stock_almacen | id_producto | productos.id |
| stock_almacen | id_almacen | almacenes.id |
| productos | categoria_id | categorias.id |
| compras | proveedor_id | proveedores.id |
| compras | almacen_id | almacenes.id |
| detalle_compras | compra_id | compras.id |
| detalle_compras | producto_id | productos.id |
| ventas | cliente_id | clientes.id |
| ventas | almacen_id | almacenes.id |
| ventas | usuario_id | users.id |
| detalle_ventas | venta_id | ventas.id |
| detalle_ventas | producto_id | productos.id |
| movimientos_inventario | producto_id | productos.id |
| movimientos_inventario | almacen_id | almacenes.id |
| role_permissions | role_id | roles.id |
| role_permissions | permission_id | permissions.id |

---

## 📊 Flujo Completo: Compra → Venta

### Paso 1: Compra (Entrada)
```
Usuario: Juan (Almacén: Central)
Acción: Compra 10 unidades del Producto A

Tablas afectadas:
├── compras: +1 registro
├── detalle_compras: +1 registro (10 unidades)
├── stock_almacen: cantidad_actual = 10 (Producto A, Almacén Central)
└── movimientos_inventario: +1 registro (tipo: COMPRA, cantidad: +10)
```

### Paso 2: Venta (Salida)
```
Usuario: Juan (Almacén: Central)
Acción: Venta 3 unidades del Producto A

Tablas afectadas:
├── ventas: +1 registro
├── detalle_ventas: +1 registro (3 unidades)
├── stock_almacen: cantidad_actual = 7 (Producto A, Almacén Central)
└── movimientos_inventario: +1 registro (tipo: VENTA, cantidad: -3)
```

### Paso 3: Consulta de Stock
```
Consulta: Stock del Producto A en Almacén Central
├── stock_almacen: cantidad_actual = 7
└── movimientos_inventario: historial completo
    ├── COMPRA: +10
    └── VENTA: -3
```

---

## 🎯 Puntos Clave

1. **Stock por Almacén**: Cada producto tiene stock independiente por almacén
2. **Trazabilidad**: Todo movimiento queda registrado en `movimientos_inventario`
3. **Usuario responsable**: Cada operación guarda el usuario que la realizó
4. **Almacén automático**: Se obtiene del usuario logueado (no del request)
5. **Seguridad**: JWT con roles para controlar acceso

---

## 👤 Autor

Creado por: Anghelo Muñoz Lopez
Fecha: 2026-03-05
