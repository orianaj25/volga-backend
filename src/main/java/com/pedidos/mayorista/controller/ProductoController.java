package com.pedidos.mayorista.controller;

import com.pedidos.mayorista.dto.ImportacionProductosDTO;
import com.pedidos.mayorista.model.Producto;
import com.pedidos.mayorista.service.ExcelProductoService;
import com.pedidos.mayorista.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {

    private final ProductoService service;
    private final ExcelProductoService excelProductoService;

    public ProductoController(
            ProductoService service,
            ExcelProductoService excelProductoService) {

        this.service = service;
        this.excelProductoService = excelProductoService;

    }

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto p) {
        return service.guardar(p);
    }

    @PutMapping("/{id}")
    public Producto editar(@PathVariable Long id, @RequestBody Producto p) {
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PostMapping("/importar")
    public ResponseEntity<?> importar(
            @RequestParam("archivo") MultipartFile archivo) {

        try {

            ImportacionProductosDTO resultado =
                    excelProductoService.importar(archivo);

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());

        }

    }
}
