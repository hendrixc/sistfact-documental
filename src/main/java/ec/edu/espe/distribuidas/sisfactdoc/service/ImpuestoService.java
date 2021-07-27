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

import ec.edu.espe.distribuidas.sisfactdoc.dao.ImpuestoRepository;
import ec.edu.espe.distribuidas.sisfactdoc.exception.CreateException;
import ec.edu.espe.distribuidas.sisfactdoc.model.Impuesto;
import ec.edu.espe.distribuidas.sisfactdoc.model.ImpuestoPorcentaje;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hendrix
 */
@Service
public class ImpuestoService {

    private final ImpuestoRepository impuestoRepo;

    public ImpuestoService(ImpuestoRepository impuestoRepo) {
        this.impuestoRepo = impuestoRepo;
    }

    public void createImpuesto(Impuesto impuesto) {
        Optional<Impuesto> impuestoOpt = this.obtenerPorCodigo(impuesto.getCodigo());
        if (!impuestoOpt.isPresent()) {
            this.impuestoRepo.save(impuesto);
        } else {
            throw new CreateException("El impuesto con codigo: " + impuesto.getCodigo() + " ya existe");
        }
    }

    public void modifyImpuesto(Impuesto impuesto) {
        this.impuestoRepo.save(impuesto);
    }

    public List<Impuesto> listAll() {
        return this.impuestoRepo.findAll();
    }

    public Optional<Impuesto> obtenerPorCodigo(String codigo) {
        return this.impuestoRepo.findByCodigo(codigo);
    }

    public void createImpuestoPorcentaje(String codigoImpuesto, BigDecimal porcentaje) {
        Optional<Impuesto> impuestoOpt = this.obtenerPorCodigo(codigoImpuesto);
        if (impuestoOpt.isPresent()) {
            Impuesto impuesto = impuestoOpt.get();
            if (impuesto.getPorcentajes() != null && !impuesto.getPorcentajes().isEmpty()) {
                for (ImpuestoPorcentaje ip : impuesto.getPorcentajes()) {
                    if (ip.getPorcentaje().compareTo(porcentaje) == 0 && "ACT".equals(ip.getEstado())) {
                        throw new CreateException("EL porcentaje: " + porcentaje + "% para el impuesto: "
                                + codigoImpuesto + " ya está registrado y tiene estado ACT");
                    }
                }
            } else {
                impuesto.setPorcentajes(new ArrayList<>());
            }
            ImpuestoPorcentaje impuestoPorcentaje = new ImpuestoPorcentaje();
            impuestoPorcentaje.setPorcentaje(porcentaje);
            impuestoPorcentaje.setEstado("INA");
            impuestoPorcentaje.setFechaInicio(new Date());
            impuestoPorcentaje.setFechaFin(null);
            impuesto.getPorcentajes().add(impuestoPorcentaje);
            this.impuestoRepo.save(impuesto);
        } else {
            throw new CreateException("No puede crear porcentaje para el impuesto: " + codigoImpuesto);
        }
    }

    public void updateImpuestoPorcentaje(String codigoImpuesto, ImpuestoPorcentaje impuestoPorcentaje) {
        Optional<Impuesto> impuestoOpt = this.obtenerPorCodigo(codigoImpuesto);
        if (impuestoOpt.isPresent()) {
            Impuesto impuesto = impuestoOpt.get();
            if (impuesto.getPorcentajes() != null && !impuesto.getPorcentajes().isEmpty()) {
                for (ImpuestoPorcentaje ip : impuesto.getPorcentajes()) {
                    if (ip.getPorcentaje().compareTo(impuestoPorcentaje.getPorcentaje()) == 0
                            && ip.getFechaInicio().equals(impuestoPorcentaje.getFechaInicio())) {
                        ip.setEstado(impuestoPorcentaje.getEstado());
                        ip.setFechaFin(impuestoPorcentaje.getFechaFin());
                        this.impuestoRepo.save(impuesto);
                        return;
                    }
                }
            }
            throw new CreateException("No existe impuestos porcentaje para actualizar en el impuesto: " + codigoImpuesto);
        }

    }

    public Impuesto listarPorcentajesDeImpuesto(String codigo) {
        Optional<Impuesto> impuestoOpt = this.impuestoRepo.findById(codigo);
        if (impuestoOpt.isPresent()) {
            return impuestoOpt.get();
        } else {
            return null;
        }
    }
}
