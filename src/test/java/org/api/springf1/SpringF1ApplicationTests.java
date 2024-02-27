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
    void contextLoads() {
    }


}
