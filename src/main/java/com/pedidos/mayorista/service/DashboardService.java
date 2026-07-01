package com.pedidos.mayorista.service;

import com.pedidos.mayorista.dto.DashboardDTO;
import com.pedidos.mayorista.repository.DetallePedidoRepository;
import com.pedidos.mayorista.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detalleRepository;

    public DashboardService(PedidoRepository pedidoRepository,
                            DetallePedidoRepository detalleRepository) {

        this.pedidoRepository = pedidoRepository;
        this.detalleRepository = detalleRepository;
    }

    public DashboardDTO obtenerDashboard() {

        LocalDate hoy = LocalDate.now();

        LocalDateTime inicio = hoy.atStartOfDay();

        LocalDateTime fin = hoy.atTime(23,59,59);

        return new DashboardDTO(

                pedidoRepository.ventasDelDia(inicio, fin),

                pedidoRepository.pedidosDelDia(inicio, fin),

                pedidoRepository.clientesDelDia(inicio, fin),

                detalleRepository.productosVendidos(inicio, fin)

        );

    }

}