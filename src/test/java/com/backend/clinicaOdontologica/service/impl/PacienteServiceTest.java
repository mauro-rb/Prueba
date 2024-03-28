package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;


    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreJuan_yRetornarSuId() {
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Pedro", "Gomez", 456391, LocalDate.of(2024, 6, 21), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Pedro", pacienteSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void deberiaBuscarPorIdAlPacienteCreado() {
        PacienteSalidaDto pacienteEncontrado = pacienteService.buscarPacientePorId(1L);
        assertNotNull(pacienteEncontrado);
        assertEquals(1L, pacienteEncontrado.getId());
    }

    @Test
    @Order(3)
    void deberiaActualizarUnPaciente() throws ResourceNotFoundException {
        Long idPaciente = 1L;
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Modificado", "Paciente", 123456, LocalDate.of(2024, 3, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));
        PacienteSalidaDto pacienteActualizado = pacienteService.modificarPaciente(pacienteEntradaDto, idPaciente);

        assertNotNull(pacienteActualizado);
        assertEquals(idPaciente, pacienteActualizado.getId());
    }

    @Test
    @Order(4)
    void deberiaEliminarseElPacienteConId1() {
        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(5)
    void deberiaLanzarResourceNotFoundExceptionAlEliminarPacienteIdEliminado(){
        try{
            pacienteService.eliminarPaciente(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(6)
    void deberiaDevolverUnaListaVaciaDePacientes() {
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();
        assertTrue(pacientes.isEmpty());
    }

}