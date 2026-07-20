package com.pedidos.mayorista.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportacionProductosDTO {

    private int importados;

    private int errores;

    private List<String> detalleErrores = new ArrayList<>();

    public int getImportados() {
        return importados;
    }

    public void setImportados(int importados) {
        this.importados = importados;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public List<String> getDetalleErrores() {
        return detalleErrores;
    }

    public void setDetalleErrores(List<String> detalleErrores) {
        this.detalleErrores = detalleErrores;
    }
}