package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exceptions.BadRequestException;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.TurnoRepository;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto;
        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoId());

        String pacienteNoEnBdd = "El paciente no se esta en la base de datos";
        String odontologoNoEnBdd = "El odontologo no esta en la base de datos";
        String ambosNulos = "El paciente y el odontologo no estan en la base de datos";

        if (paciente == null || odontologo == null) {
            if (paciente == null && odontologo == null) {
                LOGGER.error(ambosNulos);
                throw new BadRequestException(ambosNulos);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoEnBdd);
                throw new BadRequestException(pacienteNoEnBdd);
            } else {
                LOGGER.error(odontologoNoEnBdd);
                throw new BadRequestException(odontologoNoEnBdd);
            }
        } else {
            Turno turnoNuevo = turnoRepository.save(modelMapper.map(turnoEntradaDto, Turno.class));
            turnoSalidaDto = entidadADtoSalida(turnoNuevo, paciente, odontologo);
            LOGGER.info("Nuevo turno registrado: {}", turnoSalidaDto);
        }
        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoObtenido = turnoRepository.findById(id).orElse(null);
        if (turnoObtenido != null) {
            PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turnoObtenido.getPaciente().getId());
            OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turnoObtenido.getOdontologo().getId());
            TurnoSalidaDto turnoSalidaDto = entidadADtoSalida(turnoObtenido, pacienteSalidaDto, odontologoSalidaDto);
            LOGGER.info("Turno encontrado por id : {}", JsonPrinter.toString(turnoSalidaDto));
            return turnoSalidaDto;
        } else {
            LOGGER.error("No se encontro el turno con id {}", id);
            return null;
        }
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoSalidaDto> turnosSalidaDto = new ArrayList<>();
        for (Turno turno : turnos) {
            PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
            OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
            TurnoSalidaDto turnoSalidaDto = entidadADtoSalida(turno, pacienteSalidaDto, odontologoSalidaDto);
            turnosSalidaDto.add(turnoSalidaDto);
        }
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));

        return turnosSalidaDto;
    }

    @Override
    public TurnoSalidaDto modificarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws ResourceNotFoundException {
        Turno turnoObtenido = modelMapper.map(turnoEntradaDto, Turno.class);
        Turno turnoModificar = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;
        if (turnoModificar != null) {
            turnoModificar.setOdontologo(turnoObtenido.getOdontologo());
            turnoModificar.setPaciente(turnoObtenido.getPaciente());
            turnoModificar.setFechaYHora(turnoObtenido.getFechaYHora());
            turnoRepository.save(turnoModificar);
            PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turnoModificar.getPaciente().getId());
            OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turnoModificar.getOdontologo().getId());
            turnoSalidaDto = entidadADtoSalida(turnoModificar, pacienteSalidaDto, odontologoSalidaDto);
            LOGGER.warn("Turno actualizado: {}", turnoSalidaDto);
        } else {
            LOGGER.error("Error al modificar el turno, no se encontro el turno");
            throw new ResourceNotFoundException("No es posible actualizar el turno con id " + id + " ya que no se encuentra en nuestra base de datos");
        }
        return turnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) {
        Turno turnoAEliminar = turnoRepository.findById(id).orElse(null);
        if (turnoAEliminar != null) {
            turnoRepository.delete(turnoAEliminar);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
        }
    }

    private TurnoSalidaDto entidadADtoSalida(Turno turno, PacienteSalidaDto pacienteSalidaDto, OdontologoSalidaDto odontologoSalidaDto) {
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDto);
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDto);
        return turnoSalidaDto;
    }
}