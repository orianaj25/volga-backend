package com.pedidos.mayorista.service;

import com.pedidos.mayorista.dto.DashboardDTO;
import com.pedidos.mayorista.repository.DetallePedidoRepository;
import com.pedidos.mayorista.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

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

    public Map<String, Object> ventasUltimos7Dias() {

        LocalDateTime fin = LocalDateTime.now();
        LocalDateTime inicio = fin.minusDays(6);

        List<Object[]> resultados =
                pedidoRepository.ventasPorDia(inicio, fin);

        Map<LocalDate, BigDecimal> mapa = new HashMap<>();

        for (Object[] r : resultados) {
            LocalDate fecha = ((java.sql.Date) r[0]).toLocalDate();
            BigDecimal total = (BigDecimal) r[1];
            mapa.put(fecha, total);
        }

        List<Integer> ventas = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {

            LocalDate dia = LocalDate.now().minusDays(i);

            labels.add(dia.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, new Locale("es", "AR")));

            BigDecimal valor = mapa.getOrDefault(dia, BigDecimal.ZERO);

            ventas.add(valor.intValue());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", ventas);

        return response;
    }

}