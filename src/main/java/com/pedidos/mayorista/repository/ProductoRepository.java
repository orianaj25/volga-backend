package com.pedidos.mayorista.repository;

import com.pedidos.mayorista.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository  extends JpaRepository<Producto, Long> {

}
