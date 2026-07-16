package com.pedidos.mayorista.controller;

import com.pedidos.mayorista.dto.MovimientoCajaRequest;
import com.pedidos.mayorista.model.MovimientoCaja;
import com.pedidos.mayorista.service.MovimientoCajaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin("*")
public class MovimientoCajaController {

    private final MovimientoCajaService movimientoService;

    public MovimientoCajaController(
            MovimientoCajaService movimientoService) {

        this.movimientoService = movimientoService;
    }

    /*
     * ===============================
     * REGISTRAR INGRESO
     * ===============================
     */
    @PostMapping("/ingreso")
    public MovimientoCaja registrarIngreso(
            @RequestBody MovimientoCajaRequest request) {

        return movimientoService.registrarIngreso(
                request.getMonto(),
                request.getMotivo(),
                request.getUsuario()
        );

    }

    /*
     * ===============================
     * REGISTRAR RETIRO
     * ===============================
     */
    @PostMapping("/retiro")
    public MovimientoCaja registrarRetiro(
            @RequestBody MovimientoCajaRequest request) {

        return movimientoService.registrarRetiro(
                request.getMonto(),
                request.getMotivo(),
                request.getUsuario()
        );

    }

    /*
     * ===============================
     * LISTAR MOVIMIENTOS
     * ===============================
     */
    @GetMapping
    public List<MovimientoCaja> listarMovimientos() {

        return movimientoService.listarMovimientos();

    }

}