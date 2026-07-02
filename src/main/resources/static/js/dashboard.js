document.addEventListener("DOMContentLoaded", () => {

    actualizarFecha();

    cargarDashboard();

    crearGraficoVentas(); // 👈 ahora conectado al backend

});


/* =========================
   DASHBOARD TARJETAS
========================= */
async function cargarDashboard() {
    try {
        const response = await fetch("/api/dashboard");

        if (!response.ok) {
            throw new Error("Error al cargar dashboard");
        }

        const data = await response.json();

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


/* =========================
   FECHA
========================= */
function actualizarFecha() {

    const hoy = new Date();

    const opciones = {
        weekday: "long",
        day: "numeric",
        month: "long",
        year: "numeric"
    };

    const fecha = hoy.toLocaleDateString("es-AR", opciones);

    document.getElementById("fechaActual").innerHTML =
        `<i class="bi bi-calendar3"></i> ${fecha}`;
}


/* =========================
   FORMATO NÚMEROS
========================= */
function formatearNumero(numero) {
    return new Intl.NumberFormat("es-AR").format(numero);
}


/* =========================
   GRÁFICO VENTAS SEMANA
========================= */
async function crearGraficoVentas() {

    try {

        const response = await fetch("/ventas-semana");

        if (!response.ok) {
            throw new Error("Error al cargar ventas semana");
        }

        const result = await response.json();

        const ctx = document.getElementById("ventasChart");

        if (!ctx) return;

        const labels = result.labels; // 👈 del backend
        const data = result.data;     // 👈 del backend

        new Chart(ctx, {

            type: "line",

            data: {
                labels: labels,

                datasets: [{
                    label: "Ventas últimos 7 días",
                    data: data,

                    borderColor: "#3b82f6",
                    backgroundColor: "rgba(59, 130, 246, 0.15)",
                    fill: true,
                    tension: 0.4,

                    pointBackgroundColor: "#3b82f6",
                    pointRadius: 4
                }]
            },

            options: {
                responsive: true,

                plugins: {
                    legend: {
                        display: true
                    }
                }
            }

        });

    } catch (error) {
        console.error("Error cargando gráfico ventas:", error);
    }
}
