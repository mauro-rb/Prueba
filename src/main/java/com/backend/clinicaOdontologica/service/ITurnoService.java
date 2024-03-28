package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exceptions.BadRequestException;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException, ResourceNotFoundException;
    TurnoSalidaDto buscarTurnoPorId(Long id);
    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto modificarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws BadRequestException, ResourceNotFoundException;
    void eliminarTurno(Long id) throws ResourceNotFoundException;
}