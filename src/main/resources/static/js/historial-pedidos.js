const API_PEDIDOS = "/api/pedidos";

let detalleGlobal = [];

/* =========================
   FORMATEAR FECHA
========================= */

function formatearFecha(fechaIso) {

    const f = new Date(fechaIso);

    return f.toLocaleString("es-AR", {
        timeZone: "America/Argentina/Buenos_Aires",
        day: "2-digit",
        month: "long",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit"
    });
}

/* =========================
   CARGAR DETALLE
========================= */

function cargarDetalle() {

    axios.get(API_PEDIDOS + "/detalle")
        .then(response => {

            detalleGlobal = response.data;

            renderDetalle(detalleGlobal);

        })
        .catch(error => {

            console.error("Error cargando pedidos:", error);

        });
}

/* =========================
   RENDER DETALLE
========================= */

function renderDetalle(lista) {

    const tabla = document.getElementById("tablaDetalle");

    if (!tabla) return;

    tabla.innerHTML = "";

    lista.forEach(d => {

        tabla.innerHTML += `
            <tr>

                <td>${d.pedidoId}</td>

                <td>${formatearFecha(d.fecha)}</td>

                <td>${d.producto}</td>

                <td>${d.cantidad}</td>

                <td>$${d.subtotal}</td>

                <td>$${d.totalPedido}</td>

                <td>${d.metodoPago}</td>

                <td>

                    <button class="btn btn-sm btn-primary"
                        onclick="verPedido(${d.pedidoId})">

                        Ver

                    </button>

                    <button class="btn btn-sm btn-success"
                        onclick="descargarTicket(${d.pedidoId})">

                        Ticket

                    </button>

                </td>

            </tr>
        `;

    });

}

/* =========================
   FILTRAR
========================= */

function filtrarDetalle() {

    const texto = document
        .getElementById("filtro")
        .value
        .toLowerCase();

    const filtrados = detalleGlobal.filter(d =>

        d.producto.toLowerCase().includes(texto)

        ||

        d.pedidoId.toString().includes(texto)

        ||

        formatearFecha(d.fecha)
            .toLowerCase()
            .includes(texto)

    );

    renderDetalle(filtrados);

}

/* =========================
   VER PEDIDO
========================= */

function verPedido(id) {

    const pedido = detalleGlobal.filter(d => d.pedidoId === id);

    if (pedido.length === 0) return;

    document.getElementById("modalId").innerText = id;

    document.getElementById("modalFecha").innerText =
        formatearFecha(pedido[0].fecha);

    document.getElementById("modalTotal").innerText =
        pedido[0].totalPedido;

    document.getElementById("modalPago").innerText =
        pedido[0].metodoPago;

    const tabla = document.getElementById("modalItems");

    tabla.innerHTML = "";

    pedido.forEach(item => {

        tabla.innerHTML += `

            <tr>

                <td>${item.producto}</td>

                <td>${item.cantidad}</td>

                <td>$${(item.subtotal / item.cantidad).toFixed(2)}</td>

                <td>$${item.subtotal}</td>

            </tr>

        `;

    });

    new bootstrap.Modal(

        document.getElementById("modalPedido")

    ).show();

}

/* =========================
   DESCARGAR TICKET
========================= */

function descargarTicket(id) {

    window.open(`/tickets/${id}`, "_blank");

}

/* =========================
   INICIO
========================= */

document.addEventListener("DOMContentLoaded", () => {

    cargarDetalle();

});