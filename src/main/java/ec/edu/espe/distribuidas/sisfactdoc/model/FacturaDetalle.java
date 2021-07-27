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
import java.util.List;
import lombok.Data;

/**
 *
 * @author Hendrix
 */
@Data
public class FacturaDetalle {
    
    private String codigoProducto;
    private String nombre;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private List<DetalleImpuesto> impuestos;
    
}
