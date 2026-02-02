package com.pedidos.mayorista.service;

import com.pedidos.mayorista.dto.PedidoDetalleDTO;
import com.pedidos.mayorista.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDetalleService {

    @Autowired
    private DetallePedidoRepository detalleRepo;

    public List<PedidoDetalleDTO> listarDetalle() {
        return detalleRepo.listarDetalle();
    }
}
