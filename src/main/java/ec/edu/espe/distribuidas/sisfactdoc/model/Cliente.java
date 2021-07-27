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

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Hendrix
 */
@Data
@Document(collection = "clientes")
@CompoundIndexes({
    @CompoundIndex(name = "idxu_compcli_tipidid", def = "{'tipoIdentificacion': 1, 'identificacion': 1}", unique = true),
    @CompoundIndex(name = "idx_compcli_apenom", def = "{'apellido': 1, 'nombre': 1}")
})
public class Cliente {
    
    @Id
    private String id;
    private String tipoIdentificacion;
    private String identificacion;
    private String apellido;
    private String nombre;
    @Indexed(name = "idx_cliente_razonSocial")
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String genero;
    private String provincia;
    private String canton;
    private String parroquia;
}
