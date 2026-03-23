package com.proyecto.demo.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.dto.VentaDto;
import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.service.VentaService;
import com.proyecto.demo.service.ReporteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    private final VentaService ventaService;
    private final ReporteService reporteService;

    public VentaController(VentaService ventaService, ReporteService reporteService) {
        this.ventaService = ventaService;
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VentaDto>>> listarTodos(HttpServletRequest request) {
        List<VentaDto> lista = ventaService.findAll();
        System.out.println("Ventas encontradas: " + lista);
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de ventas", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VentaDto>> getById(@PathVariable Long id, HttpServletRequest request) {
        VentaDto venta = ventaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(venta, "Venta encontrada", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VentaDto>> crearVenta(@RequestBody VentaDto venta, HttpServletRequest request) {
        VentaDto nueva = ventaService.realizarVenta(venta);
        return ResponseEntity.ok(ApiResponse.success(nueva, "Venta creada", request.getRequestURI()));
    }

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
