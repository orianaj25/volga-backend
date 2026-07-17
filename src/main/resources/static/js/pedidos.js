const API_PROD = "/api/productos";
const API_PEDIDOS = "/api/pedidos";

let productos = [];
let productosGlobal = [];
let seleccion = {};

/* =========================
   CARGAR PRODUCTOS
========================= */

function cargarProductos() {

    axios.get(API_PROD)
        .then(response => {

            productos = response.data;
            productosGlobal = response.data;

            renderProductos(productos);

        })
        .catch(error => {

            console.error("Error cargando productos:", error);

        });

}

/* =========================
   RENDER PRODUCTOS
========================= */

function renderProductos(lista) {

    const tabla = document.getElementById("tabla");

    if (!tabla) return;

    tabla.innerHTML = "";

    lista.forEach(producto => {

        if (!seleccion[producto.id]) {

            seleccion[producto.id] = {

                activo: false,
                cantidad: 1

            };

        }

        tabla.innerHTML += `

            <tr>

                <td>

                    <input
                        type="checkbox"
                        ${seleccion[producto.id].activo ? "checked" : ""}
                        onchange="toggle(${producto.id}, this.checked)">

                </td>

                <td>${producto.nombre}</td>

                <td>$${producto.precioVenta}</td>

                <td>

                    <input
                        type="number"
                        step="0.001"
                        min="0.001"
                        value="${seleccion[producto.id].cantidad}"
                        class="form-control form-control-sm"
                        style="width:80px"
                        onchange="cambiarCantidad(${producto.id}, this.value)"
                        ${seleccion[producto.id].activo ? "" : "disabled"}
                        id="cant-${producto.id}">

                </td>

                <td>

                    $
                    <span id="sub-${producto.id}">
                        0
                    </span>

                </td>

            </tr>

        `;

    });

    recalcular();

}

/* =========================
   FILTRAR PRODUCTOS
========================= */

function filtrarProductos() {

    const texto = document
        .getElementById("filtroProductos")
        .value
        .toLowerCase();

    renderProductos(

        productosGlobal.filter(producto =>

            producto.nombre
                .toLowerCase()
                .includes(texto)

        )

    );

}

/* =========================
   ACTIVAR PRODUCTO
========================= */

function toggle(id, checked) {

    seleccion[id].activo = checked;

    document.getElementById("cant-" + id).disabled = !checked;

    recalcular();

}

/* =========================
   CAMBIAR CANTIDAD
========================= */

function cambiarCantidad(id, cantidad) {

      seleccion[id].cantidad = Number(cantidad);

    recalcular();

}

/* =========================
   RECALCULAR TOTAL
========================= */

function recalcular() {

    let total = 0;

    productosGlobal.forEach(producto => {

        const seleccionado = seleccion[producto.id];

        let subtotal = 0;

        if (seleccionado && seleccionado.activo) {

            subtotal = producto.precioVenta * seleccionado.cantidad;

            total += subtotal;

        }

        const celdaSubtotal = document.getElementById("sub-" + producto.id);

        if (celdaSubtotal) {

            celdaSubtotal.innerText = subtotal.toFixed(2);

        }

    });

    const totalElemento = document.getElementById("total");

    if (totalElemento) {

        totalElemento.innerText = total.toFixed(2);

    }

}

/* =========================
   MOSTRAR DNI
========================= */

function toggleDni() {

    const metodo = document.getElementById("metodoPago").value;

    const contenedor = document.getElementById("dniContainer");

    if (metodo === "MP_TRANSFERENCIA") {

        contenedor.style.display = "block";

    } else {

        contenedor.style.display = "none";

        document.getElementById("dniCliente").value = "";

    }

}

/* =========================
   GUARDAR PEDIDO
========================= */

function guardarPedido() {

    const items = [];

    for (const id in seleccion) {

        if (seleccion[id].activo) {

            items.push({

                productoId: Number(id),

                cantidad: seleccion[id].cantidad

            });

        }

    }

    const metodo = document.getElementById("metodoPago").value;

    const dni = document.getElementById("dniCliente").value;

    if (items.length === 0) {

        alert("Debe seleccionar al menos un producto");

        return;

    }

    if (!metodo) {

        alert("Debe seleccionar un método de pago");

        return;

    }

    axios.post(API_PEDIDOS, {

        items: items,

        metodoPago: metodo,

        dniCliente: dni

    })
    .then(() => {

        alert("Pedido guardado correctamente.");

        window.location.href = "historial-pedidos.html";

    })
    .catch(error => {

        console.error(error);

        alert("No fue posible guardar el pedido.");

    });

}

/* =========================
   INICIO
========================= */

document.addEventListener("DOMContentLoaded", () => {

    cargarProductos();

    const metodoPago = document.getElementById("metodoPago");

    if (metodoPago) {

        metodoPago.addEventListener("change", toggleDni);

    }

});