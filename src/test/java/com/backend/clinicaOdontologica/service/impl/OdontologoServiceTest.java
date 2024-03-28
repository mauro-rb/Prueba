package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnOdontologodeNombreRamiro() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("013456789","Ramiro","Test");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Ramiro", odontologoSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void deberiaBuscarPorIdAlOdontologoCreado() {
        OdontologoSalidaDto odontologoEncontrado = odontologoService.buscarOdontologoPorId(1L);
        assertNotNull(odontologoEncontrado);
        assertEquals(1l, odontologoEncontrado.getId());
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaDeOdontologosQueNoEsteVacia() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();
        assertFalse(odontologos.isEmpty());
    }

    @Test
    @Order(4)
    void deberiaActualizarUnOdontologo() throws ResourceNotFoundException {
        Long idOdontologo = 1L;
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("08264728391","Modificado","Segundo");
        OdontologoSalidaDto odontologoActualizado = odontologoService.modificarOdontologo(odontologoEntradaDto, idOdontologo);

        assertNotNull(odontologoActualizado);
        assertEquals(idOdontologo, odontologoActualizado.getId());
    }

    @Test
    @Order(5)
    void deberiaBorrarUnOdontolgo() {
        Long idOdontologo = 1L;
        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(idOdontologo));
        assertNull(odontologoService.buscarOdontologoPorId(idOdontologo));
    }

    @Test
    @Order(6)
    void deberiaDevolverUnaListaDeOdontologosVacia() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();
        assertTrue(odontologos.isEmpty());
    }
}