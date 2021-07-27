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

import ec.edu.espe.distribuidas.sisfactdoc.dto.ImpuestoPorcentajeRQ;
import ec.edu.espe.distribuidas.sisfactdoc.dto.ImpuestoPorcentajeRS;
import ec.edu.espe.distribuidas.sisfactdoc.model.Impuesto;
import ec.edu.espe.distribuidas.sisfactdoc.model.ImpuestoPorcentaje;
import ec.edu.espe.distribuidas.sisfactdoc.service.ImpuestoService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hendrix
 */
@RestController
@RequestMapping("/api/impuesto/porcentaje")
public class ImpuestoPorcentajeController {
    
    private final ImpuestoService service;

    public ImpuestoPorcentajeController(ImpuestoService service) {
        this.service = service;
    }
    
    @GetMapping(value = "{codigo}")
    public ResponseEntity obtenerPorcentajesImpuesto(@PathVariable("codigo") String codigo) {
        try {
            Impuesto impuesto = this.service.listarPorcentajesDeImpuesto(codigo);
            List<ImpuestoPorcentajeRS> response = new ArrayList<>();
            for (ImpuestoPorcentaje ip : impuesto.getPorcentajes()) {
                response.add(ImpuestoPorcentajeRS.builder()
                        .codigoImpuesto(impuesto.getCodigo())
                        .porcentaje(ip.getPorcentaje())
                        .estado(ip.getEstado())
                        .fechaInicio(ip.getFechaInicio())
                        .fechaFin(ip.getFechaFin()).build());
                
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity crear(@RequestBody ImpuestoPorcentajeRQ request) {
        this.service.createImpuestoPorcentaje(request.getCodigoImpuesto(), request.getPorcentaje());
        return ResponseEntity.ok().build();
    }
    
    @PutMapping
    public ResponseEntity actualizar(@RequestBody ImpuestoPorcentajeRQ request) {
        try {
            ImpuestoPorcentaje ip = new ImpuestoPorcentaje();
            ip.setPorcentaje(request.getPorcentaje());
            ip.setEstado(request.getEstado());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            ip.setFechaInicio(sdf.parse(request.getFechaInicio()));
            if (request.getFechaFin()!=null) {
                ip.setFechaFin(sdf.parse(request.getFechaFin()));
            }
            this.service.updateImpuestoPorcentaje(request.getCodigoImpuesto(), ip);
            return ResponseEntity.ok().build();
        } catch (ParseException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
