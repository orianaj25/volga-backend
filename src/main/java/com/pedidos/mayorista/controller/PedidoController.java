package com.pedidos.mayorista.controller;

import com.pedidos.mayorista.dto.PedidoDetalleDTO;
import com.pedidos.mayorista.dto.PedidoRequest;
import com.pedidos.mayorista.model.Pedido;
import com.pedidos.mayorista.service.PedidoDetalleService;
import com.pedidos.mayorista.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;
    @Autowired
    private PedidoDetalleService detalleService;

    @PostMapping
    public Pedido crear(@RequestBody PedidoRequest request) {
        return service.crearPedido(request);
    }

    @GetMapping("/detalle")
    public List<PedidoDetalleDTO> detalle() {
        return detalleService.listarDetalle();
    }

}
