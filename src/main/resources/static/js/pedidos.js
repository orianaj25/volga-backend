const API_PROD = "/api/productos";
  const API_PEDIDOS = "/api/pedidos";

  let productos = [];
  let productosGlobal = [];
  let seleccion = {};
  let detalleGlobal = [];

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

  function cargarProductos() {
      axios.get(API_PROD).then(r => {
          productos = r.data;
          productosGlobal = r.data;
          renderProductos(productos);
      });
  }

  function renderProductos(lista) {
      const t = document.getElementById("tabla");
      t.innerHTML = "";

      lista.forEach(p => {

          if (!seleccion[p.id]) {
              seleccion[p.id] = {
                  activo: false,
                  cantidad: 1
              };
          }

          t.innerHTML += `
          <tr>
              <td>
                  <input type="checkbox"
                      ${seleccion[p.id].activo ? "checked" : ""}
                      onchange="toggle(${p.id}, this.checked)">
              </td>

              <td>${p.nombre}</td>

              <td>$${p.precioVenta}</td>

              <td>
                  <input type="number"
                      min="1"
                      value="${seleccion[p.id].cantidad}"
                      class="form-control form-control-sm"
                      style="width:80px"
                      onchange="cambiarCantidad(${p.id}, this.value)"
                      ${seleccion[p.id].activo ? "" : "disabled"}
                      id="cant-${p.id}">
              </td>

              <td>
                  $ <span id="sub-${p.id}">0</span>
              </td>
          </tr>`;
      });

      recalcular();
  }

  function filtrarProductos() {

      const txt =
          document.getElementById("filtroProductos")
          .value
          .toLowerCase();

      renderProductos(
          productosGlobal.filter(p =>
              p.nombre.toLowerCase().includes(txt)
          )
      );
  }

  function toggle(id, checked) {

      seleccion[id].activo = checked;

      document.getElementById("cant-" + id).disabled = !checked;

      recalcular();
  }

  function cambiarCantidad(id, cant) {

      seleccion[id].cantidad = parseInt(cant);

      recalcular();
  }

  function recalcular() {

      let total = 0;

      productosGlobal.forEach(p => {

          const sel = seleccion[p.id];

          let sub = 0;

          if (sel && sel.activo) {

              sub = p.precioVenta * sel.cantidad;

              total += sub;
          }

          const el = document.getElementById("sub-" + p.id);

          if (el) {
              el.innerText = sub.toFixed(2);
          }
      });

      document.getElementById("total").innerText =
          total.toFixed(2);
  }

  /* =========================
     MOSTRAR DNI OPCIONAL
  ========================= */

  function toggleDni() {

      const metodo =
          document.getElementById("metodoPago").value;

      const contenedor =
          document.getElementById("dniContainer");

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

          const s = seleccion[id];

          if (s.activo) {

              items.push({
                  productoId: +id,
                  cantidad: s.cantidad
              });
          }
      }

      const metodo =
          document.getElementById("metodoPago").value;

      const dni =
          document.getElementById("dniCliente").value;

      if (!items.length) {
          return alert("Debe seleccionar al menos un producto");
      }

      if (!metodo) {
          return alert("Debe seleccionar un método de pago");
      }

      axios.post(API_PEDIDOS, {

          items: items,

          metodoPago: metodo,

          dniCliente: dni

      }).then(() => {

          alert("Pedido guardado");

          cargarDetalle();

          location.reload();
      });
  }

  /* =========================
     CARGAR DETALLE
  ========================= */

  function cargarDetalle() {

      axios.get(API_PEDIDOS + "/detalle")
          .then(r => {

              detalleGlobal = r.data;

              renderDetalle(detalleGlobal);
          });
  }

  /* =========================
     RENDER DETALLE
  ========================= */

  function renderDetalle(lista) {

      const t =
          document.getElementById("tablaDetalle");

      t.innerHTML = "";

      lista.forEach(d => {

          t.innerHTML += `
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

          </tr>`;
      });
  }

  /* =========================
     FILTRAR DETALLE
  ========================= */

  function filtrarDetalle() {

      const txt =
          document.getElementById("filtro")
          .value
          .toLowerCase();

      renderDetalle(

          detalleGlobal.filter(d =>

              d.producto.toLowerCase().includes(txt)

              ||

              d.pedidoId.toString().includes(txt)

              ||

              formatearFecha(d.fecha)
                  .toLowerCase()
                  .includes(txt)
          )
      );
  }

  /* =========================
     VER PEDIDO
  ========================= */

  function verPedido(id) {

      const pedido =
          detalleGlobal.filter(d => d.pedidoId === id);

      document.getElementById("modalId").innerText = id;

      document.getElementById("modalFecha").innerText =
          formatearFecha(pedido[0].fecha);

      document.getElementById("modalTotal").innerText =
          pedido[0].totalPedido;

      document.getElementById("modalPago").innerText =
          pedido[0].metodoPago;

      const t =
          document.getElementById("modalItems");

      t.innerHTML = "";

      pedido.forEach(i => {

          t.innerHTML += `
          <tr>

              <td>${i.producto}</td>

              <td>${i.cantidad}</td>

              <td>
                  $${(i.subtotal / i.cantidad).toFixed(2)}
              </td>

              <td>$${i.subtotal}</td>

          </tr>`;
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
     INIT
  ========================= */

  cargarProductos();

  cargarDetalle();