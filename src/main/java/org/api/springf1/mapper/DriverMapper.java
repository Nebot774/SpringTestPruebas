package org.api.springf1.mapper;

import org.api.springf1.dto.DriverDTO;
import org.api.springf1.model.Constructor;
import org.api.springf1.model.Driver;

public class DriverMapper {
    public static DriverDTO toDriverDTO(Driver driver) {
        return DriverDTO.builder()
                .id(driver.getId())
                .code(driver.getCode())
                .forename(driver.getForename())
                .surname(driver.getSurname())
                .constructor(driver.getConstructor() != null ? driver.getConstructor().getName() : null)
                .build();
    }

    public static Driver toDriver(DriverDTO driverDTO) {
        Driver driver = new Driver();
        driver.setId(driverDTO.id());
        driver.setCode(driverDTO.code());
        driver.setForename(driverDTO.forename());
        driver.setSurname(driverDTO.surname());

        Constructor constructor = new Constructor();
        constructor.setName(driverDTO.constructor());
        driver.setConstructor(constructor);

        return driver;
    }


}
