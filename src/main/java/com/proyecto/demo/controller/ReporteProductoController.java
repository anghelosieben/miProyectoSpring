package com.proyecto.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.service.ReporteProdutoService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteProductoController {
    @Autowired
    private ReporteProdutoService reporteService;

    @GetMapping("/productos/base64")
    public ResponseEntity<Map<String, String>> descargarReporteBase64() {
        Map<String, String> response = new HashMap<>();
        try {
            // Llamamos al método que ya creaste en tu servicio
            String base64 = reporteService.generarListaProductosPDFBase64();
            
            response.put("pdfBase64", base64);
            response.put("fileName", "lista_productos.pdf");
            response.put("contentType", "application/pdf");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            // Log del error (puedes usar un logger real aquí)
            e.printStackTrace(); 
            
            response.put("error", "Error al generar el reporte: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
