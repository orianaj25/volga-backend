package com.pedidos.mayorista.service;

import com.pedidos.mayorista.dto.PedidoRequest;
import com.pedidos.mayorista.model.DetallePedido;
import com.pedidos.mayorista.model.Pedido;
import com.pedidos.mayorista.model.Producto;
import com.pedidos.mayorista.repository.PedidoRepository;
import com.pedidos.mayorista.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    public Pedido crearPedido(PedidoRequest request) {

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (PedidoRequest.ItemPedido item : request.items) {

            Producto p = productoRepo.findById(item.productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));

            BigDecimal subtotal = p.getPrecioVenta()
                    .multiply(BigDecimal.valueOf(item.cantidad));

            DetallePedido d = new DetallePedido();
            d.setPedido(pedido);
            d.setProducto(p);
            d.setCantidad(item.cantidad);
            d.setSubtotal(subtotal);

            detalles.add(d);
            total = total.add(subtotal);
        }

        pedido.setTotal(total);
        pedido.setDetalles(detalles);

        return pedidoRepo.save(pedido);
    }

    public List<Pedido> listar() {
        return pedidoRepo.findAll();
    }


}
