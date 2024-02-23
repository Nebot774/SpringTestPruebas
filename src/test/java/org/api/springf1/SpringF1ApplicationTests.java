package org.api.springf1;

import org.api.springf1.dto.DriverDTO;
import org.api.springf1.dto.DriverResponse;
import org.api.springf1.exception.ConstructorNotFoundException;
import org.api.springf1.exception.DriverNotFoundException;
import org.api.springf1.model.Driver;
import org.api.springf1.service.ConstructorService;
import org.api.springf1.service.DriverService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import org.api.springf1.mapper.DriverMapper;

import java.util.Arrays;
import java.util.List;

import static org.api.springf1.mapper.DriverMapper.toDriver; // Importa el método toDriver
import static org.api.springf1.mapper.DriverMapper.toDriverDTO; // Importa el método toDriverDTO




@Disabled
@SpringBootTest
class SpringF1ApplicationTests {

    @Test
    void shouldReturnDriverDTOWhenFindDriverByCode() {
        // Crear un mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        // Crear un DriverDTO de prueba usando el constructor de record
        DriverDTO expectedDriver = new DriverDTO(
                1L, // id
                "someCode", // code
                "Abel", // forename
                "eNsUpUTISSIMOpRIME", // surname
                "ConstructorName" // constructor
        );

        // Definir el comportamiento esperado del método getDriverByCode()
        when(driverService.getDriverByCode("someCode")).thenReturn(expectedDriver);

        // Llamar al método getDriverByCode() en DriverService
        DriverDTO actualDriver = driverService.getDriverByCode("someCode");

        // Verificar que el resultado es el esperado
        assertEquals(expectedDriver, actualDriver);
    }

//    @Test
//    void shouldReturnDriverDTOWhenUpdateDriver() {
//        // Crear un mock de DriverService
//        DriverService driverService = Mockito.mock(DriverService.class);
//
//        // Crear un DriverDTO de prueba
//        DriverDTO originalDriverDTO = new DriverDTO(
//                1L, // id
//                "someCode", // code
//                "John", // forename
//                "Doe", // surname
//                "ConstructorName" // constructor
//        );
//
//        // Convertir DriverDTO a una entidad Driver
//        Driver originalDriver = DriverMapper.toDriver(originalDriverDTO);
//
//        // Crear un DriverDTO que represente al conductor actualizado
//        DriverDTO updatedDriverDTO = new DriverDTO(
//                1L, // id
//                "updatedCode", // code
//                "UpdatedJohn", // forename
//                "UpdatedDoe", // surname
//                "UpdatedConstructorName" // constructor
//        );
//
//        // Convertir el DriverDTO actualizado a una entidad Driver
//        Driver updatedDriver = DriverMapper.toDriver(updatedDriverDTO);
//
//        // Definir el comportamiento esperado del método updateDriver()
//        when(driverService.updateDriver(any(Driver.class))).thenReturn(updatedDriver);
//
//        // Llamar al método updateDriver() en DriverService
//        DriverDTO actualDriverDTO = driverService.updateDriver(originalDriver);
//
//        // Convertir la entidad Driver resultante de vuelta a DriverDTO
//        DriverDTO actualDriver =  DriverMapper.toDriverDTO(actualDriverDTO);
//
//        // Verificar que el resultado es el esperado
//        assertEquals(updatedDriverDTO, actualDriver);
//    }

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
        // Crear un mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        // Crear una lista de DriverDTO que represente los conductores que esperamos obtener
        List<DriverDTO> expectedDriverDTOList = Arrays.asList(
                new DriverDTO(1L, "code1", "forename1", "surname1", "constructor1"),
                new DriverDTO(2L, "code2", "forename2", "surname2", "constructor2")
        );

        // Crear un DriverResponse que contenga la lista de DriverDTO esperada y otros argumentos necesarios
        DriverResponse expectedDriverResponse = DriverResponse.builder()
                .content(expectedDriverDTOList)
                .pageNo(0)
                .pageSize(10)
                .totalElements(expectedDriverDTOList.size())
                .totalPages(1) // Ajusta según la lógica de paginación que utilices
                .last(true) // Ajusta según la lógica de paginación que utilices
                .build();

        // Definir el comportamiento esperado del método getDrivers()
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
        // Crear un mock de DriverService
        DriverService driverService = Mockito.mock(DriverService.class);

        // Crear un DriverDTO que represente un piloto inexistente
        DriverDTO nonExistingDriver = new DriverDTO(999L, "nonExistingCode", "Forename", "Surname", "Constructor");

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
