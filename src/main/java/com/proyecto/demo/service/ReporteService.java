package com.proyecto.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.entity.Venta;
import com.proyecto.demo.repository.VentasRepository;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReporteService {
    private final VentasRepository ventaRepository;
    
    public ReporteService(VentasRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
    
    /**
     * Genera reporte de venta en formato PDF devolviendo bytes puros
     * @param ventaId ID de la venta
     * @return PDF en bytes
     * @throws Exception si ocurre error al generar el reporte
     */
    public byte[] generarReporteVentaPDF(Long ventaId) throws Exception {
        JasperPrint jasperPrint = generarJasperPrint(ventaId);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    /**
     * Genera reporte de venta en formato PDF codificado en Base64
     * Ideal para enviar a través de JSON a aplicaciones como Angular
     * @param ventaId ID de la venta
     * @return PDF en Base64
     * @throws Exception si ocurre error al generar el reporte
     */
    public String generarReporteVentaPDFBase64(Long ventaId) throws Exception {
        byte[] pdfBytes = generarReporteVentaPDF(ventaId);
        return Base64.getEncoder().encodeToString(pdfBytes);
    }
    
    /**
     * Genera reporte de venta en formato Excel (XLSX) devolviendo bytes puros
     * @param ventaId ID de la venta
     * @return XLSX en bytes
     * @throws Exception si ocurre error al generar el reporte
     */
    public byte[] generarReporteVentaExcel(Long ventaId) throws Exception {
        // Nota: Para Excel se necesita una dependencia adicional (jasperreports-excel)
        // Por ahora retornamos el PDF como alternativa
        JasperPrint jasperPrint = generarJasperPrint(ventaId);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    /**
     * Genera reporte de venta en formato Excel (XLSX) en Base64
     * @param ventaId ID de la venta
     * @return XLSX en Base64
     * @throws Exception si ocurre error al generar el reporte
     */
    public String generarReporteVentaExcelBase64(Long ventaId)  throws Exception {
        byte[] excelBytes = generarReporteVentaExcel(ventaId);
        return Base64.getEncoder().encodeToString(excelBytes);
    }
    
    /**
     * Método privado que genera el JasperPrint con todos los parámetros
     */
    @Transactional(readOnly = true)
    private JasperPrint generarJasperPrint(Long ventaId) throws Exception {
        // Obtener datos de la venta
        Venta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + ventaId));


        // Preparar parámetros para el reporte
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ventaId", venta.getId());
        parametros.put("cliente", venta.getCliente() != null ? venta.getCliente().getNombre() : "Cliente Desconocido");
        parametros.put("fecha", venta.getFechaRegistro());
        parametros.put("monto", venta.getTotal() != null ? venta.getTotal() : new BigDecimal("0"));
        parametros.put("empresa", "Mi Empresa S.A.");

        // Cargar y compilar el reporte JRXML del archivo de recursos
        String reportePath = "reports/report.jrxml";
        InputStream jrxmlStream = new ClassPathResource(reportePath).getInputStream();
        
        // Compilar JRXML a JasperReport en memoria
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        // Llenar el reporte con parámetros y datos vacíos (para este ejemplo usamos JREmptyDataSource)
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

        return jasperPrint;
    }
}
