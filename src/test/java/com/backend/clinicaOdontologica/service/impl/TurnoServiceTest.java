package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exceptions.BadRequestException;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private  OdontologoService odontologoService;
    @Autowired
    private  PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarUnTurnoCorrectamente() throws BadRequestException {
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Paciente", "Gonzalez", 509874659, LocalDate.of(2024, 3, 10), new DomicilioEntradaDto("calle", 95620, "Localidad", "Provincia"));
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("AN-213132312", "Odontologo", "Diaz");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(1L,1L, LocalDateTime.of(2024,8,8,16,00));

            TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
            assertEquals(1L, turnoSalidaDto.getId());
            assertNotNull(turnoSalidaDto.getId());
    }

    @Test
    @Order(2)
    void deberiaBuscarPorIdElTurnoCreado() {
        TurnoSalidaDto turnoEncontrado = turnoService.buscarTurnoPorId(1L);
        assertNotNull(turnoEncontrado);
        assertEquals(1L, turnoEncontrado.getId());
    }

    @Test
    @Order(3)
    void deberiaLanzarBadRequestAlRegistrarTurnoConOdontologoInexistente(){
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(1L, 8L, LocalDateTime.of(2023,12,10,20,30));
        assertThrows(BadRequestException.class, () -> turnoService.registrarTurno(turnoEntradaDto));
    }

    @Test
    @Order(4)
    void deberiaEncontrarElTurnoConId1() {
        TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(1L);
        assertEquals(1L, turnoSalidaDto.getId());
    }

    @Test
    @Order(5)
    public void deberiaEliminarTurnoConId1() {
        long idTurno = 1L;
        assertDoesNotThrow(() -> turnoService.eliminarTurno(idTurno));
    }

    @Test
    @Order(6)
    void deberiaDevolverUnaListaDeTurnosVacia() {
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();
        assertTrue(turnos.isEmpty());
    }
}