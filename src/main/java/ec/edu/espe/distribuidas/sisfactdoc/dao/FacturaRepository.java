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
package ec.edu.espe.distribuidas.sisfactdoc.dao;

import ec.edu.espe.distribuidas.sisfactdoc.model.Factura;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Hendrix
 */
public interface FacturaRepository extends MongoRepository<Factura, String>{
    
    List<Factura> findByClienteTipoIdentificacionAndClienteIdentificacionOrderByFechaDesc(String tipoIdentificacion, 
            String identificacion);
    
    Factura findByNumeroAutorizacion(String numeroAutorizacion);
    
    List<Factura> findByFechaBetween(Date inicio, Date fin);
    
    Factura findByCodigoEstablecimientoAndPuntoEmisionAndSecuencial(String codigoEstablecimiento, String puntoEmision, 
            Integer secuencial);
    
    
}
