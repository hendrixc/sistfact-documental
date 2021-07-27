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
package ec.edu.espe.distribuidas.sisfactdoc.service;

import ec.edu.espe.distribuidas.sisfactdoc.dao.ProductoRepository;
import ec.edu.espe.distribuidas.sisfactdoc.model.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hendrix
 */
@Service
public class ProductoService {
    
    private final ProductoRepository productoRepo;
    
    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }
    
    public Producto obtainByCodigo(String codigo) {
        Optional<Producto> productoOpt = this.productoRepo.findById(codigo);
        if (productoOpt.isPresent()) {
            return productoOpt.get();
        } else {
            throw new RuntimeException("Not found");
        }
    }
    
    public List<Producto> listByEstado(String estado) {
        return this.productoRepo.findByEstadoOrderByNombre(estado);
    }
    
}
