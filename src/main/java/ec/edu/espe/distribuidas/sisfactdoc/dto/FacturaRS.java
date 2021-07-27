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
package ec.edu.espe.distribuidas.sisfactdoc.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Hendrix
 */
@Data
@Builder
public class FacturaRS {
    
    private String numeroAutorizacion;
    private String establecimiento;
    private String puntoEmision;
    private String secuencial;
    private Date fecha;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String tipoIdentificacion;
    private String identificacion;
    private String nombre;
    
    private List<FacturaDetalleRS> detalles;
    private List<FacturaImpuestoRS> impuestos;
}
