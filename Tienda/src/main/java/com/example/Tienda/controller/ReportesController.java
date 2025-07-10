package com.example.Tienda.controller;

import com.example.Tienda.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportesController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private UsuarioService usuarioService;

    // Ranking de productos más vendidos
    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<Object[]>> obtenerProductosMasVendidos() {
        try {
            List<Object[]> ranking = productoService.obtenerProductosMasVendidos();
            return new ResponseEntity<>(ranking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Ranking de productos mejor valorados
    @GetMapping("/productos-mejor-valorados")
    public ResponseEntity<List<Object[]>> obtenerProductosMejorValorados() {
        try {
            List<Object[]> ranking = reporteService.obtenerProductosMejorValorados();
            return new ResponseEntity<>(ranking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Estadísticas de ventas por período
    @GetMapping("/ventas-periodo")
    public ResponseEntity<Map<String, Object>> obtenerVentasPorPeriodo(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);

            Map<String, Object> estadisticas = new HashMap<>();

            // Total de ventas
            Double totalVentas = reporteService.calcularTotalVentasPorPeriodo(inicio, fin);
            estadisticas.put("totalVentas", totalVentas != null ? totalVentas : 0.0);

            // Número de transacciones
            Long numTransacciones = reporteService.contarTransaccionesPorPeriodo(inicio, fin);
            estadisticas.put("numeroTransacciones", numTransacciones != null ? numTransacciones : 0);

            // Venta promedio
            Double ventaPromedio = (totalVentas != null && numTransacciones != null && numTransacciones > 0)
                    ? totalVentas / numTransacciones : 0.0;
            estadisticas.put("ventaPromedio", ventaPromedio);

            return new ResponseEntity<>(estadisticas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Productos con bajo stock
    @GetMapping("/productos-bajo-stock")
    public ResponseEntity<List<Object[]>> obtenerProductosBajoStock(@RequestParam(defaultValue = "10") int limite) {
        try {
            List<Object[]> productos = reporteService.obtenerProductosStockBajo(limite);
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Resumen general del negocio
    @GetMapping("/resumen-general")
    public ResponseEntity<Map<String, Object>> obtenerResumenGeneral() {
        try {
            Map<String, Object> resumen = reporteService.obtenerDashboardGeneral();
            return new ResponseEntity<>(resumen, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
