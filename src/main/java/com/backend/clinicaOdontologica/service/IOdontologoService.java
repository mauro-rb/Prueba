package com.backend.clinicaOdontologica.service;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;


import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto modificarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) throws ResourceNotFoundException;
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;
}