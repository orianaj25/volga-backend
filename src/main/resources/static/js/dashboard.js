document.addEventListener("DOMContentLoaded", () => {
    cargarDashboard();
});

async function cargarDashboard() {
    try {
        const response = await fetch("/api/dashboard");

        if (!response.ok) {
            throw new Error("Error al cargar dashboard");
        }

        const data = await response.json();

        // =========================
        // ACTUALIZAR TARJETAS
        // =========================

        document.getElementById("ventasDia").innerText =
            "$" + formatearNumero(data.ventasDelDia);

        document.getElementById("pedidosDia").innerText =
            data.pedidosDelDia;

        document.getElementById("clientesDia").innerText =
            data.clientesAtendidos;

        document.getElementById("productosDia").innerText =
            data.productosVendidos;

    } catch (error) {
        console.error("Error cargando dashboard:", error);
    }
}

/**
 * Formatea números tipo 100000 -> 100.000
 */
function formatearNumero(numero) {
    return new Intl.NumberFormat("es-AR").format(numero);
}