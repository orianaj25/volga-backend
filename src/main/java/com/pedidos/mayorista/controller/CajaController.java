package com.pedidos.mayorista.controller;

import com.pedidos.mayorista.dto.AbrirCajaRequest;
import com.pedidos.mayorista.dto.CajaHistorialDTO;
import com.pedidos.mayorista.dto.CerrarCajaRequest;
import com.pedidos.mayorista.dto.MovimientoCajaRequest;
import com.pedidos.mayorista.model.Caja;
import com.pedidos.mayorista.model.MovimientoCaja;
import com.pedidos.mayorista.service.CajaService;
import com.pedidos.mayorista.service.MovimientoCajaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/caja")
public class CajaController {

    private final CajaService cajaService;
    private final MovimientoCajaService movimientoCajaService;

    public CajaController(
            CajaService cajaService,
            MovimientoCajaService movimientoCajaService) {

        this.cajaService = cajaService;
        this.movimientoCajaService = movimientoCajaService;
    }

    /*
     * Abrir Caja
     */

    @PostMapping("/abrir")
    public Caja abrirCaja(
            @RequestBody AbrirCajaRequest request) {

        return cajaService.abrirCaja(
                request.getCajaInicial(),
                request.getUsuario()
        );
    }

    /*
     * Obtener Caja Abierta
     */

    @GetMapping("/abierta")
    public Optional<Caja> cajaAbierta() {

        return cajaService.obtenerCajaAbierta();

    }

    /*
     * Registrar Ingreso
     */

    @PostMapping("/ingreso")
    public MovimientoCaja ingreso(
            @RequestBody MovimientoCajaRequest request) {

        return movimientoCajaService.registrarIngreso(
                request.getMonto(),
                request.getMotivo(),
                request.getUsuario()
        );

    }

    /*
     * Registrar Retiro
     */

    @PostMapping("/retiro")
    public MovimientoCaja retiro(
            @RequestBody MovimientoCajaRequest request) {

        return movimientoCajaService.registrarRetiro(
                request.getMonto(),
                request.getMotivo(),
                request.getUsuario()
        );

    }

    /*
     * Cerrar Caja
     */

    @PostMapping("/cerrar")
    public Caja cerrarCaja(
            @RequestBody CerrarCajaRequest request) {

        return cajaService.cerrarCaja(
                request.getEfectivoContado(),
                request.getObservaciones()
        );

    }

    /*
     * Historial de Cajas
     */

    @GetMapping("/historial")
    public List<CajaHistorialDTO> historial() {

        return cajaService.listar();

    }

    /*
     * Movimientos de la caja abierta
     */

    @GetMapping("/movimientos")
    public Iterable<MovimientoCaja> movimientos() {

        return movimientoCajaService.listarMovimientos();

    }

    /*
     * Movimientos de una caja
     */

    @GetMapping("/{id}/movimientos")
    public Iterable<MovimientoCaja> movimientosCaja(
            @PathVariable Long id) {

        return movimientoCajaService.listarPorCaja(id);

    }

}