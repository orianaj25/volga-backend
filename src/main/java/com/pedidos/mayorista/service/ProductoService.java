package com.pedidos.mayorista.service;

import com.pedidos.mayorista.model.Producto;
import com.pedidos.mayorista.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto p) {
        return repo.save(p);
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Producto actualizar(Long id, Producto nuevo) {
        return repo.findById(id)
                .map(p -> {
                    p.setNombre(nuevo.getNombre());
                    p.setCosto(nuevo.getCosto());
                    p.setPrecioVenta(nuevo.getPrecioVenta());
                    return repo.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}

