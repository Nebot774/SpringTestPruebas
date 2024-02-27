package org.api.springf1.repository;

import org.api.springf1.dto.DriverDTO;
import org.api.springf1.dto.DriverResponse;
import org.api.springf1.exception.ConstructorNotFoundException;
import org.api.springf1.exception.DriverNotFoundException;
import org.api.springf1.mapper.DriverMapper;
import org.api.springf1.model.Driver;
import org.api.springf1.repository.DriverRepository;
import org.api.springf1.service.ConstructorService;
import org.api.springf1.service.DriverService;
import org.api.springf1.service.DriverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DriverRepositoryTest {



    @Test
    void shouldReturnDriverDTOWhenFindDriverByCode() {
        // Crear un mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        // Crear un DriverDTO de prueba usando el constructor de record
        DriverDTO expectedDriver = new DriverDTO(
                1L, // id
                "2", // code
                "Abel", // forename
                "eNsUpUTISSIMOpRIME", // surname
                "Xulos" // constructor
        );

        // Definir el comportamiento esperado del método getDriverByCode()
        when(driverService.getDriverByCode("someCode")).thenReturn(expectedDriver);

        // Llamar al método getDriverByCode() en DriverService
        DriverDTO actualDriver = driverService.getDriverByCode("someCode");

        // Verificar que el resultado es el esperado
        assertEquals(expectedDriver, actualDriver);
    }


    @Test
    void shouldReturnNothingWhenDeleteDriverByCode() {
        // Crear un mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        // Definir el código del conductor a eliminar
        String code = "alo";

        // Llamar al método deleteDriverByCode() en DriverService
        driverService.deleteDriverByCode(code);

        // Verificar que el método deleteDriverByCode() fue llamado con el código correcto
        Mockito.verify(driverService).deleteDriverByCode(code);
    }


    @Test
    void shouldReturnDriverResponseWhenGetAllDrivers() {
        // un mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        //  lista de DriverDTO que represente los conductores que esperamos obtener
        List<DriverDTO> expectedDriverDTOList = Arrays.asList(
                new DriverDTO(1L, "2", "Abel", "Gimenez Franco", "Serifruit"),
                new DriverDTO(2L, "4", "Fernando", "Bea BOU", "Alcachofa")
        );

        //  DriverResponse que contenga la lista de DriverDTO esperada y otros argumentos necesarios
        DriverResponse expectedDriverResponse = DriverResponse.builder()
                .content(expectedDriverDTOList)
                .pageNo(0)
                .pageSize(10)
                .totalElements(expectedDriverDTOList.size())
                .totalPages(1)
                .last(true)
                .build();

        // comportamiento esperado del método getDrivers()
        when(driverService.getDrivers(0, 10)).thenReturn(expectedDriverResponse);

        // Llamar al método getDrivers() en DriverService
        DriverResponse actualDriverResponse = driverService.getDrivers(0, 10);

        // Verificar que el resultado es la lista de DriverDTO que esperábamos
        assertEquals(expectedDriverResponse, actualDriverResponse);
    }

    @Test
    void shouldThrowConstructorNotFoundExceptionWhenConstructorDoesNotExist() {
        // Crear un mock de ConstructorService
        ConstructorService constructorService = Mockito.mock(ConstructorService.class);

        // Simular el comportamiento para lanzar ConstructorNotFoundException
        when(constructorService.getConstructorByRef(anyString()))
                .thenThrow(new ConstructorNotFoundException("Constructor not found"));


        // Verificar que se lanza la excepción
        assertThrows(ConstructorNotFoundException.class, () -> {
            constructorService.getConstructorByRef("34");
        });
    }


    @Test
    void shouldThrowDriverNotFoundExceptionWhenUpdateNonExistingDriver() {
        //  mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        // DriverDTO que represente un piloto inexistente
        DriverDTO nonExistingDriver = new DriverDTO(999L, "juan", "luis", "guerra", "valladolid");

        // Simular el comportamiento del servicio para lanzar DriverNotFoundException
        when(driverService.updateDriver(any(Driver.class)))
                .thenThrow(new DriverNotFoundException("Driver not found"));

        // Verificar que se lanza DriverNotFoundException
        Driver driver = DriverMapper.toDriver(nonExistingDriver);

        // Verificar que se lanza DriverNotFoundException
        assertThrows(DriverNotFoundException.class, () -> {
            driverService.updateDriver(driver);
        });
    }



}
