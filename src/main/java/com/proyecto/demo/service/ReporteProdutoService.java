package com.proyecto.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.repository.ProductoRepository;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReporteProdutoService {
    @Autowired
    private final ProductoRepository productoRepository;

    public ReporteProdutoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public byte[] generarListaProductosPDF() throws Exception {
        // 1. Obtener todos los productos
        List<Producto> productos = productoRepository.findAll();
        System.out.println("Productos encontrados: " + productos.size());
        //System.out.println("Productos encontrados: " + productos);
        // 2. Parámetros del reporte
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Lista de Productos");
        parametros.put("fechaGeneracion", new java.util.Date());
        parametros.put("empresa", "Mi Empresa S.A.");
        //parametros.put("ProductoDataSet", productos);

        // 3. DataSource con la lista de productos
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productos);

        // 4. Cargar el reporte compilado (.jasper)
        String jasperPath = "reports/Producto.jasper";
        InputStream jasperStream = new ClassPathResource(jasperPath).getInputStream();

        // 5. Llenar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

        // 6. Exportar a PDF
        ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfOutput));

        SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
        config.setMetadataAuthor("Mi Proyecto");
        config.setMetadataTitle("Lista de Productos");
        exporter.setConfiguration(config);

        exporter.exportReport();

        return pdfOutput.toByteArray();
    }

    /**
     * Versión Base64 para Angular
     */
    public String generarListaProductosPDFBase64() throws Exception {
        byte[] pdfBytes = generarListaProductosPDF();
        return Base64.getEncoder().encodeToString(pdfBytes);
    }
}

