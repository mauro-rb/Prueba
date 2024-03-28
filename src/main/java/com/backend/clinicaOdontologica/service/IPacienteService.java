package com.backend.clinicaOdontologica.service;


import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;


import java.util.List;

public interface IPacienteService {
    PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente);
    PacienteSalidaDto buscarPacientePorId(Long id);
    List<PacienteSalidaDto> listarPacientes();
    PacienteSalidaDto modificarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) throws ResourceNotFoundException;
    void eliminarPaciente(Long id) throws ResourceNotFoundException;
}