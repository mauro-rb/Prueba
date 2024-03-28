package com.backend.clinicaOdontologica.controller;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exceptions.BadRequestException;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private ITurnoService turnoService;
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarTurno(@RequestBody @Valid TurnoEntradaDto turno) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.registrarTurno(turno), HttpStatus.CREATED);
    }

    //GET
    @GetMapping("listar/{id}")
    public TurnoSalidaDto buscarTurnoPorId(@PathVariable Long id)
    {
        return turnoService.buscarTurnoPorId(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }
    //PUT
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto turno, @PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.modificarTurno(turno, id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException
    {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}