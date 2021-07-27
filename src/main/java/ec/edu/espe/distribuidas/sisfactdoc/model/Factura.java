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
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Hendrix
 */
@Data
@Document(collection = "facturas")
public class Factura {
    
    @Id
    private String id;
    private ClienteFactura cliente;
    @Indexed(name = "idx_factura_establecimiento")
    private String codigoEstablecimiento;
    private String puntoEmision;
    private Integer secuencial;
    private String numeroAutorizacion;
    @Indexed(name = "idx_factura_fecha", direction = IndexDirection.DESCENDING)
    private Date fecha;
    private BigDecimal subtotal;
    private BigDecimal total;
    private List<FacturaDetalle> detalles;
    private List<DetalleImpuesto> impuestos;
}
