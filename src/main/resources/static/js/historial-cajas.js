const API_CAJA = "/api/caja";

let cajas = [];

/*==============================
FORMATEAR FECHA
==============================*/

function formatearFecha(fecha){

    if(!fecha) return "-";

    return new Date(fecha).toLocaleString("es-AR",{

        timeZone:"America/Argentina/Buenos_Aires",

        day:"2-digit",

        month:"2-digit",

        year:"numeric",

        hour:"2-digit",

        minute:"2-digit"

    });

}

/*==============================
FORMATO MONEDA
==============================*/

function dinero(valor){

    if(valor==null) return "$0";

    return "$"+Number(valor).toLocaleString("es-AR",{
        minimumFractionDigits:2,
        maximumFractionDigits:2
    });

}

/*==============================
CARGAR HISTORIAL
==============================*/

function cargarHistorial(){

    axios.get(API_CAJA+"/historial")

        .then(res=>{

            cajas=res.data;

            renderTabla(cajas);

        })

        .catch(err=>{

            console.error(err);

            alert("No se pudo cargar el historial.");

        });

}

/*==============================
RENDER TABLA
==============================*/

function renderTabla(lista){

    const tabla=document.getElementById("tablaCajas");

    tabla.innerHTML="";

    lista.forEach(caja=>{

        let diferenciaClase="neutro";

        if(caja.diferencia>0){

            diferenciaClase="positivo";

        }

        if(caja.diferencia<0){

            diferenciaClase="negativo";

        }

        tabla.innerHTML+=`

        <tr>

            <td>${caja.id}</td>

            <td>${caja.usuario}</td>

            <td>${formatearFecha(caja.fechaApertura)}</td>

            <td>${formatearFecha(caja.fechaCierre)}</td>

            <td>

                <span class="badge bg-${caja.estado==="ABIERTA"?"success":"secondary"}">

                    ${caja.estado}

                </span>

            </td>

            <td class="${diferenciaClase}">

                ${dinero(caja.diferencia)}

            </td>

            <td>

                <button
                    class="btn btn-primary btn-sm"
                    onclick="verCaja(${caja.id})">

                    Ver

                </button>

            </td>

        </tr>

        `;

    });

}

/*==============================
BUSCAR
==============================*/

function filtrarCaja(){

    const texto=document
        .getElementById("buscarCaja")
        .value
        .toLowerCase();

    const lista=cajas.filter(c=>

        c.usuario.toLowerCase().includes(texto)

        ||

        formatearFecha(c.fechaApertura)
            .toLowerCase()
            .includes(texto)

        ||

        String(c.id).includes(texto)

    );

    renderTabla(lista);

}

/*==============================
VER DETALLE
==============================*/

function verCaja(id){

    const caja=cajas.find(c=>c.id===id);

    if(!caja)return;

    document.getElementById("mUsuario").innerText=caja.usuario;

    document.getElementById("mEstado").innerText=caja.estado;

    document.getElementById("mApertura").innerText=
        formatearFecha(caja.fechaApertura);

    document.getElementById("mCierre").innerText=
        formatearFecha(caja.fechaCierre);

    document.getElementById("mCajaInicial").innerText=
        dinero(caja.cajaInicial);

    document.getElementById("mVentasEfectivo").innerText=
        dinero(caja.ventasEfectivo);

    document.getElementById("mVentasDigitales").innerText=
        dinero(caja.ventasDigitales);

    document.getElementById("mIngresos").innerText=
        dinero(caja.ingresos);

    document.getElementById("mRetiros").innerText=
        dinero(caja.retiros);

    document.getElementById("mEsperado").innerText=
        dinero(caja.efectivoEsperado);

    document.getElementById("mContado").innerText=
        dinero(caja.efectivoContado);

    const diferencia=document.getElementById("mDiferencia");

    diferencia.innerText=dinero(caja.diferencia);

    diferencia.className="";

    if(caja.diferencia>0){

        diferencia.classList.add("positivo");

        diferencia.innerText+=" (Sobrante)";

    }
    else if(caja.diferencia<0){

        diferencia.classList.add("negativo");

        diferencia.innerText+=" (Faltante)";

    }
    else{

        diferencia.classList.add("neutro");

        diferencia.innerText+=" (Caja Exacta)";

    }

    document.getElementById("mObservaciones").innerText=
        caja.observaciones || "-";

    new bootstrap.Modal(

        document.getElementById("modalCaja")

    ).show();

}

/*==============================
INICIO
==============================*/

document.addEventListener("DOMContentLoaded",()=>{

    cargarHistorial();

});