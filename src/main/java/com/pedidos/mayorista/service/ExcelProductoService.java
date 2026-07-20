package com.pedidos.mayorista.service;

import com.pedidos.mayorista.dto.ImportacionProductosDTO;
import com.pedidos.mayorista.model.Producto;
import com.pedidos.mayorista.repository.ProductoRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ExcelProductoService {

    private final ProductoRepository productoRepository;

    public ExcelProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ImportacionProductosDTO importar(MultipartFile archivo) throws IOException {

        ImportacionProductosDTO resultado = new ImportacionProductosDTO();

        Workbook workbook = WorkbookFactory.create(archivo.getInputStream());

        Sheet hoja = workbook.getSheetAt(0);

        for (int i = 1; i <= hoja.getLastRowNum(); i++) {

            Row fila = hoja.getRow(i);

            if (fila == null) {
                continue;
            }

            try {

                String nombre = obtenerTexto(fila.getCell(0));

                if (nombre.isBlank()) {
                    throw new RuntimeException("Nombre vacío");
                }

                if (productoRepository.existsByNombreIgnoreCase(nombre)) {
                    throw new RuntimeException("Producto duplicado");
                }

                String tipoVenta =
                        obtenerTexto(fila.getCell(3)).toUpperCase();

                if (!tipoVenta.equals("UNIDAD")
                        && !tipoVenta.equals("KILOGRAMO")) {

                    throw new RuntimeException(
                            "Tipo de venta inválido"
                    );

                }

                Producto producto = new Producto();

                producto.setNombre(nombre);

                producto.setCosto(
                        obtenerDouble(fila.getCell(1))
                );

                producto.setPrecioVenta(
                        obtenerBigDecimal(fila.getCell(2))
                );

                producto.setTipoVenta(tipoVenta);

                productoRepository.save(producto);

                resultado.setImportados(
                        resultado.getImportados() + 1
                );

            } catch (Exception e) {

                resultado.setErrores(
                        resultado.getErrores() + 1
                );

                resultado.getDetalleErrores().add(
                        "Fila " + (i + 1) + ": " + e.getMessage()
                );

            }

        }

        workbook.close();

        return resultado;
    }

    private String obtenerTexto(Cell cell) {

        if (cell == null) {
            return "";
        }

        cell.setCellType(CellType.STRING);

        return cell.getStringCellValue().trim();
    }

    private Double obtenerDouble(Cell cell) {

        if (cell == null) {
            return 0.0;
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }

        return Double.parseDouble(
                cell.getStringCellValue().replace(",", ".")
        );
    }

    private BigDecimal obtenerBigDecimal(Cell cell) {

        if (cell == null) {
            return BigDecimal.ZERO;
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(
                    cell.getNumericCellValue()
            );
        }

        return new BigDecimal(
                cell.getStringCellValue().replace(",", ".")
        );
    }

}