const API_CAJA = "/api/caja";

let cajaActual = null;

/*==============================
FECHA Y HORA
==============================*/

function cargarFechaHora() {

    const ahora = new Date();

    document.getElementById("fechaActual").value =
        ahora.toLocaleDateString("es-AR");

    document.getElementById("horaActual").value =
        ahora.toLocaleTimeString("es-AR");

}

/*==============================
FORMATO MONEDA
==============================*/

function dinero(valor){

    return "$ " + Number(valor || 0)
        .toLocaleString("es-AR",{
            minimumFractionDigits:2
        });

}

/*==============================
VERIFICAR CAJA ABIERTA
==============================*/

function verificarCaja(){

    axios.get(API_CAJA + "/abierta")

        .then(res=>{

            if(res.data){

                cajaActual = res.data;

                mostrarCaja();

            }else{

                mostrarApertura();

            }

        })

        .catch(()=>{

            mostrarApertura();

        });

}

/*==============================
MOSTRAR APERTURA
==============================*/

function mostrarApertura(){

    document.getElementById("panelApertura").style.display="block";

    document.getElementById("panelCaja").style.display="none";

}

/*==============================
MOSTRAR CAJA
==============================*/

function mostrarCaja(){

    document.getElementById("panelApertura").style.display="none";

    document.getElementById("panelCaja").style.display="block";

    actualizarResumen();

    cargarMovimientos();

}

/*==============================
ACTUALIZAR RESUMEN
==============================*/

function actualizarResumen(){

    document.getElementById("lblCajaInicial").innerHTML =
        dinero(cajaActual.cajaInicial);

    document.getElementById("lblVentasEfectivo").innerHTML =
        dinero(cajaActual.ventasEfectivo);

    document.getElementById("lblVentasDigitales").innerHTML =
        dinero(cajaActual.ventasDigitales);

    document.getElementById("lblEsperado").innerHTML =
        dinero(cajaActual.efectivoEsperado);

}

/*==============================
ABRIR CAJA
==============================*/

function abrirCaja(){

    const body={

        usuario:
            document.getElementById("usuario").value,

        cajaInicial:
            Number(document.getElementById("cajaInicial").value)

    };

    axios.post(API_CAJA+"/abrir",body)

        .then(res=>{

            cajaActual=res.data;

            mostrarCaja();

            alert("Caja abierta correctamente.");

        })

        .catch(err=>{

            alert(err.response?.data?.message || "Error");

        });

}

/*==============================
INGRESO
==============================*/

function registrarIngreso(){

    const body={

        usuario:cajaActual.usuario,

        monto:Number(
            document.getElementById("ingresoMonto").value
        ),

        motivo:
            document.getElementById("ingresoMotivo").value

    };

    axios.post(API_CAJA+"/ingreso",body)

        .then(()=>{

            refrescarCaja();

            document.getElementById("ingresoMonto").value="";

            document.getElementById("ingresoMotivo").value="";

        });

}

/*==============================
RETIRO
==============================*/

function registrarRetiro(){

    const body={

        usuario:cajaActual.usuario,

        monto:Number(
            document.getElementById("retiroMonto").value
        ),

        motivo:
            document.getElementById("retiroMotivo").value

    };

    axios.post(API_CAJA+"/retiro",body)

        .then(()=>{

            refrescarCaja();

            document.getElementById("retiroMonto").value="";

            document.getElementById("retiroMotivo").value="";

        });

}

/*==============================
REFRESCAR
==============================*/

function refrescarCaja(){

    axios.get(API_CAJA+"/abierta")

        .then(res=>{

            cajaActual=res.data;

            actualizarResumen();

            cargarMovimientos();

        });

}

/*==============================
MOVIMIENTOS
==============================*/

function cargarMovimientos(){

    axios.get(API_CAJA+"/movimientos")

        .then(res=>{

            const tabla=document.getElementById("tablaMovimientos");

            tabla.innerHTML="";

            res.data.forEach(m=>{

                tabla.innerHTML+=`

                    <tr>

                        <td>

                            ${new Date(m.fecha)
                                .toLocaleString("es-AR")}

                        </td>

                        <td>

                            ${m.tipo}

                        </td>

                        <td>

                            ${dinero(m.monto)}

                        </td>

                        <td>

                            ${m.motivo}

                        </td>

                        <td>

                            ${m.usuario}

                        </td>

                    </tr>

                `;

            });

        });

}
/*==============================
CERRAR CAJA
==============================*/

function cerrarCaja() {

    const body = {

        efectivoContado: Number(
            document.getElementById("efectivoContado").value
        ),

        observaciones:
            document.getElementById("observaciones").value

    };

    axios.post(API_CAJA + "/cerrar", body)

        .then(response => {

            const caja = response.data;

            let mensaje =
                "=================================\n" +
                "        CIERRE DE CAJA\n" +
                "=================================\n\n" +

                "Efectivo esperado: $" +
                Number(caja.efectivoEsperado).toLocaleString("es-AR") +

                "\n\nEfectivo contado: $" +
                Number(caja.efectivoContado).toLocaleString("es-AR") +

                "\n\n";

            if (Number(caja.diferencia) > 0) {

                mensaje +=
                    "🟡 SOBRANTE: $" +
                    Number(caja.diferencia).toLocaleString("es-AR");

            } else if (Number(caja.diferencia) < 0) {

                mensaje +=
                    "🔴 FALTANTE: $" +
                    Math.abs(Number(caja.diferencia)).toLocaleString("es-AR");

            } else {

                mensaje +=
                    "🟢 Sin diferencias.";

            }

            alert(mensaje);

            location.reload();

        })

        .catch(err => {

            alert(
                err.response?.data?.message ||
                "Error al cerrar la caja."
            );

        });

}

/*==============================
INICIO
==============================*/

document.addEventListener("DOMContentLoaded",()=>{

    cargarFechaHora();

    verificarCaja();

});