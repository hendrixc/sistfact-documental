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

import ec.edu.espe.distribuidas.sisfactdoc.model.Impuesto;
import ec.edu.espe.distribuidas.sisfactdoc.service.ImpuestoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/impuesto")
@Slf4j
public class ImpuestoController {
    
    private final ImpuestoService service;

    public ImpuestoController(ImpuestoService impuestoService) {
        this.service = impuestoService;
    }
    
    @GetMapping
    public ResponseEntity listarTodos() {
        List<Impuesto> impuestos = this.service.listAll();
        return ResponseEntity.ok(impuestos);
    }
    
    @GetMapping(value = "{codigo}")
    public ResponseEntity obtenerPorCodigo(@PathVariable("codigo") String codigo) {
        log.info("Obteniendo Impuesto por codigo: {} esperando por resultado", codigo);
        return ResponseEntity.of(this.service.obtenerPorCodigo(codigo));
    }
    
    @PostMapping
    public ResponseEntity crearImpuesto(@RequestBody Impuesto impuesto) {
        try {
            log.info("Va a crear el impuesto con la siguiente informacion: {}", impuesto);
            this.service.createImpuesto(impuesto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ocurrio un error al crear el impuesto. {} - retorna badrequest", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping
    public ResponseEntity modificarImpuesto(@RequestBody Impuesto impuesto) {
        try {
            this.service.modifyImpuesto(impuesto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    
}
