package com.proyecto.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.model.entity.Venta;
import com.proyecto.demo.service.VentaService;
import com.proyecto.demo.service.ReporteService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    private final VentaService ventaService;
    private final ReporteService reporteService;

    public VentaController(VentaService ventaService, ReporteService reporteService) {
        this.ventaService = ventaService;
        this.reporteService = reporteService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.realizarVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }
    
    /**
     * Endpoint para descargar reporte de venta en PDF
     * @param ventaId ID de la venta
     * @return PDF descargable
     */
    @GetMapping("/{ventaId}/reporte/pdf")
    public ResponseEntity<byte[]> descargarReportePDF(@PathVariable Long ventaId) {
        try {
            byte[] reporte = reporteService.generarReporteVentaPDF(ventaId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.attachment()
                    .filename("Reporte_Venta_" + ventaId + ".pdf")
                    .build()
            );
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(reporte);
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para descargar reporte de venta en Excel
     * @param ventaId ID de la venta
     * @return XLSX descargable
     */
    @GetMapping("/{ventaId}/reporte/excel")
    public ResponseEntity<byte[]> descargarReporteExcel(@PathVariable Long ventaId) {
        try {
            byte[] reporte = reporteService.generarReporteVentaExcel(ventaId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(
                ContentDisposition.attachment()
                    .filename("Reporte_Venta_" + ventaId + ".xlsx")
                    .build()
            );
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(reporte);
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint para obtener reporte de venta en PDF Base64 (para Angular)
     * @param ventaId ID de la venta
     * @return JSON con reporte en Base64
     */
    @GetMapping("/{ventaId}/reporte/pdf-base64")
    public ResponseEntity<Map<String, String>> obtenerReportePdfBase64(@PathVariable Long ventaId) {
        try {
            String reporteBase64 = reporteService.generarReporteVentaPDFBase64(ventaId);
            
            Map<String, String> response = new HashMap<>();
            response.put("contenido", reporteBase64);
            response.put("nombre", "Reporte_Venta_" + ventaId + ".pdf");
            response.put("tipo", "application/pdf");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No fue posible generar el reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Endpoint para obtener reporte de venta en Excel Base64 (para Angular)
     * @param ventaId ID de la venta
     * @return JSON con reporte en Base64
     */
    @GetMapping("/{ventaId}/reporte/excel-base64")
    public ResponseEntity<Map<String, String>> obtenerReporteExcelBase64(@PathVariable Long ventaId) {
        try {
            String reporteBase64 = reporteService.generarReporteVentaExcelBase64(ventaId);
            
            Map<String, String> response = new HashMap<>();
            response.put("contenido", reporteBase64);
            response.put("nombre", "Reporte_Venta_" + ventaId + ".xlsx");
            response.put("tipo", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No fue posible generar el reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
