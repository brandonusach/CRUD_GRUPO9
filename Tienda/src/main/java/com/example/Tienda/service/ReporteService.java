package com.example.Tienda.service;

import com.example.Tienda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReporteService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    // Reporte de productos más vendidos
    public List<Object[]> obtenerProductosMasVendidos() {
        return productoRepository.findProductosMasVendidos();
    }

    // Reporte de productos mejor valorados
    public List<Object[]> obtenerProductosMejorValorados() {
        return valoracionRepository.findProductosMejorValorados();
    }

    // Calcular total de ventas por período
    public Double calcularTotalVentasPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return boletaRepository.sumVentasPorPeriodo(fechaInicio, fechaFin);
    }

    // Contar transacciones por período
    public Long contarTransaccionesPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return boletaRepository.countTransaccionesPorPeriodo(fechaInicio, fechaFin);
    }

    // Reporte de usuarios más activos
    public List<Object[]> obtenerUsuariosMasActivos() {
        return usuarioRepository.findUsuariosMasActivos();
    }

    // Reporte de productos por categoría


    // Reporte de stock bajo
    public List<Object[]> obtenerProductosStockBajo(Integer stockMinimo) {
        return productoRepository.findProductosStockBajo(stockMinimo);
    }

    // Dashboard general
    public Map<String, Object> obtenerDashboardGeneral() {
        Map<String, Object> dashboard = new HashMap<>();

        // Estadísticas básicas
        dashboard.put("totalProductos", productoRepository.count());
        dashboard.put("totalUsuarios", usuarioRepository.count());
        dashboard.put("ventasDelMes", boletaRepository.sumVentasDelMes());

        // Top productos
        dashboard.put("topProductos", productoRepository.findTop5ProductosMasVendidos());

        // Productos sin stock
        dashboard.put("productosSinStock", productoRepository.countProductosSinStock());

        // Productos con stock
        dashboard.put("productosConStock", productoRepository.countByStockGreaterThan(0));

        return dashboard;
    }

    // Reporte específico: Ranking de cartas más solicitadas
    public List<Object[]> obtenerRankingCartasMasSolicitadas() {
        return productoRepository.findRankingCartasMasSolicitadas();
    }

}
