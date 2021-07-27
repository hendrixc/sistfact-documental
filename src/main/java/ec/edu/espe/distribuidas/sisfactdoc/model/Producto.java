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
package ec.edu.espe.distribuidas.sisfactdoc.model;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Hendrix
 */
@Data
@Document(collection = "productos")
public class Producto {
    
    @Id
    private String id;
    @Indexed(name = "idxu_producto_codigo", unique = true)
    private String codigo;
    @Indexed(name = "idx_producto_nombre")
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal existencia;
    private String estado;
    private Boolean iva;
    private Boolean ice;
}
