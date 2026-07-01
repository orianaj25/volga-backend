// =====================================================
// VOLGA - Productos
// =====================================================

const API = "/api/productos";

let editandoId = null;

// =====================================================
// CARGAR PRODUCTOS
// =====================================================

function cargar() {

    axios.get(API)
        .then(response => {

            const tabla = document.getElementById("tabla");

            tabla.innerHTML = "";

            response.data.forEach(producto => {

                tabla.innerHTML += `
                    <tr>

                        <td>${producto.nombre}</td>

                        <td>${producto.costo}</td>

                        <td>${producto.precioVenta}</td>

                        <td>

                            <button
                                class="btn btn-sm btn-warning"
                                onclick="cargarEdicion(
                                    ${producto.id},
                                    '${producto.nombre}',
                                    ${producto.costo},
                                    ${producto.precioVenta}
                                )">

                                Editar

                            </button>

                            <button
                                class="btn btn-sm btn-danger ms-1"
                                onclick="eliminar(${producto.id})">

                                Borrar

                            </button>

                        </td>

                    </tr>
                `;

            });

        })
        .catch(error => {

            console.error("Error cargando productos", error);

        });

}

// =====================================================
// GUARDAR
// =====================================================

function guardar() {

    const producto = {

        nombre: document.getElementById("nombre").value,

        costo: document.getElementById("costo").value,

        precioVenta: document.getElementById("precio").value

    };

    if (editandoId == null) {

        axios.post(API, producto)

            .then(() => {

                limpiar();

            })

            .catch(error => {

                console.error(error);

            });

    } else {

        axios.put(API + "/" + editandoId, producto)

            .then(() => {

                editandoId = null;

                limpiar();

                document.getElementById("tituloForm").innerText =
                    "Nuevo Producto";

                document
                    .getElementById("cancelarBtn")
                    .classList.add("d-none");

            })

            .catch(error => {

                console.error(error);

            });

    }

}

// =====================================================
// EDITAR
// =====================================================

function cargarEdicion(id, nombre, costo, precio) {

    editandoId = id;

    document.getElementById("nombre").value = nombre;

    document.getElementById("costo").value = costo;

    document.getElementById("precio").value = precio;

    document.getElementById("tituloForm").innerText =
        "Editar Producto";

    document
        .getElementById("cancelarBtn")
        .classList.remove("d-none");

}

// =====================================================
// CANCELAR
// =====================================================

function cancelar() {

    editandoId = null;

    limpiar();

    document.getElementById("tituloForm").innerText =
        "Nuevo Producto";

    document
        .getElementById("cancelarBtn")
        .classList.add("d-none");

}

// =====================================================
// ELIMINAR
// =====================================================

function eliminar(id) {

    if (!confirm("¿Seguro que desea eliminar este producto?")) {

        return;

    }

    axios.delete(API + "/" + id)

        .then(() => {

            cargar();

        })

        .catch(error => {

            console.error(error);

        });

}

// =====================================================
// FILTRAR
// =====================================================

function filtrarProductos() {

    const texto =
        document
            .getElementById("filtro")
            .value
            .toLowerCase();

    const filas =
        document.querySelectorAll("#tabla tr");

    filas.forEach(fila => {

        const nombre =
            fila.children[0]
                .innerText
                .toLowerCase();

        fila.style.display =
            nombre.includes(texto)
                ? ""
                : "none";

    });

}

// =====================================================
// LIMPIAR
// =====================================================

function limpiar() {

    document.getElementById("nombre").value = "";

    document.getElementById("costo").value = "";

    document.getElementById("precio").value = "";

    cargar();

}

// =====================================================
// INICIO
// =====================================================

document.addEventListener("DOMContentLoaded", () => {

    cargar();

});