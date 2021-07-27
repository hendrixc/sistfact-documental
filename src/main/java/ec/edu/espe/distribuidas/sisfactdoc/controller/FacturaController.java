/*
 * Copyright (c) 2021 Hendrix.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Hendrix - initial API and implementation and/or initial documentation
 */
package ec.edu.espe.distribuidas.sisfactdoc.controller;

import ec.edu.espe.distribuidas.sisfactdoc.dto.FacturaDetalleRQ;
import ec.edu.espe.distribuidas.sisfactdoc.dto.FacturaRQ;
import ec.edu.espe.distribuidas.sisfactdoc.dto.FacturaRS;
import ec.edu.espe.distribuidas.sisfactdoc.model.ClienteFactura;
import ec.edu.espe.distribuidas.sisfactdoc.model.Factura;
import ec.edu.espe.distribuidas.sisfactdoc.model.FacturaDetalle;
import ec.edu.espe.distribuidas.sisfactdoc.service.FacturaService;
import ec.edu.espe.distribuidas.sisfactdoc.transform.FacturaRSTransform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hendrix
 */
@Slf4j
@RestController
@RequestMapping("/api/factura")
public class FacturaController {
    
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }
    
    @GetMapping(value = "{fechaInicio}/{fechaFin}")
    public ResponseEntity obtenerFacturasPorFechas(@PathVariable("fechaInicio") String fechaInicio, 
            @PathVariable("fechaFin") String fechaFin) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicioD;
        Date fechaFinD;
        try {
            fechaInicioD = sdf.parse(fechaInicio);
            fechaFinD = sdf.parse(fechaFin);
        } catch (ParseException pe) {
            return ResponseEntity.badRequest().build();
        }
        List<Factura> facturas = this.facturaService.obtenerPorFechas(fechaInicioD, fechaFinD);
        log.debug("Facturas obtenidas {} con los parametros: {} - ()", facturas.size(), fechaInicio, fechaFin);
        List<FacturaRS> facturasRS = new ArrayList<>();
        facturas.forEach(factura -> {
            facturasRS.add(FacturaRSTransform.buildFacturaRS(factura));
        });
        return ResponseEntity.ok(facturasRS);
    }
    
    @GetMapping(value= "{numeroAutorizacion}")
    public ResponseEntity obtenerFactura(@PathVariable("numeroAutorizacion") String numeroAutorizacion) {
        log.info("Va a recuperar la factura con numero de autorizacion: {}", numeroAutorizacion);
        Factura factura = this.facturaService.obtenerPorNumeroAutorizacion(numeroAutorizacion);
        if (factura!=null) {
            FacturaRS facturaRS = FacturaRSTransform.buildFacturaRSComplete(factura);
            return ResponseEntity.ok(facturaRS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity registrar(@RequestBody FacturaRQ facturaRQ) {
        try {
            Factura factura = new Factura();
            ClienteFactura cf = new ClienteFactura();
            cf.setIdentificacion(facturaRQ.getIdentificacion());
            cf.setTipoIdentificacion(facturaRQ.getTipoIdentificacion());
            factura.setCliente(cf);
            factura.setCodigoEstablecimiento(facturaRQ.getEstablecimiento());
            factura.setPuntoEmision(facturaRQ.getPuntoEmision());
            factura.setSecuencial(facturaRQ.getSecuencial());
            List<FacturaDetalle> detalles = new ArrayList<>();
            for (FacturaDetalleRQ detalleRQ : facturaRQ.getDetalles()) {
                FacturaDetalle detalle = new FacturaDetalle();
                detalle.setCodigoProducto(detalleRQ.getCodigoProducto());
                detalle.setCantidad(detalleRQ.getCantidad());
                detalles.add(detalle);
            }
            factura.setDetalles(detalles);
            Factura facturaResp = this.facturaService.crearFactura(factura);
            log.info("La info de la factura es: {}", facturaResp);
            //TODO: Transformar la factura a factura RS y retornar en el body
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            log.error("Error al resgitrar la facrura: {}", facturaRQ, e);
            return ResponseEntity.badRequest().build();
        }
    }
}


