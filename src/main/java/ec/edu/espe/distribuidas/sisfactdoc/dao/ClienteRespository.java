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

import ec.edu.espe.distribuidas.sisfactdoc.model.Cliente;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Hendrix
 */
public interface ClienteRespository extends MongoRepository<Cliente, String>{
    
    Cliente findByTipoIdentificacionAndIdentificacion(String tipoIdentificacion, String identificacion);
    
    List<Cliente> findByApellidoStartingWithOrderByApellido(String apellido);
    
    List<Cliente> findByRazonSocialStartingWithOrderByRazonSocial(String razonSocial);
    
    List<Cliente> findByTelefonoOrderByApellidoAscNombreAscRazonSocialAsc(String telefono);
    
}
