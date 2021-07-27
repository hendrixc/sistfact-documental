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

import ec.edu.espe.distribuidas.sisfactdoc.model.Producto;
import ec.edu.espe.distribuidas.sisfactdoc.service.ProductoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hendrix
 */
@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping(value = "{codigo}")
    public ResponseEntity obtenerProducto(@PathVariable("codigo") String codigo) {
        try {
            Producto producto = this.service.obtainByCodigo(codigo);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity obtenerActivos() {
        List<Producto> productos = this.service.listByEstado("ACT");
        return ResponseEntity.ok(productos);
    }

}
