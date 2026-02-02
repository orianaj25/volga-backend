package com.pedidos.mayorista.repository;

import com.pedidos.mayorista.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}

