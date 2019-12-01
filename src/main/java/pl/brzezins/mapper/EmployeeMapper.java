package pl.brzezins.mapper;

import pl.brzezins.dto.AddressDto;
import pl.brzezins.dto.EmployeeDto;
import pl.brzezins.entity.Address;
import pl.brzezins.entity.Employee;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
public class EmployeeMapper implements DtoMapper<EmployeeDto, Employee> {
    @Inject
    private AddressMapper addressMapper;

    @Override
    public EmployeeDto convert(Employee employee) {
        List<Address> addresses = employee.getAddresses();
        List<AddressDto> addressesDto = Collections.emptyList();

        if (addresses != null && !addresses.isEmpty()) {
            addressesDto = addresses.stream().map(addressMapper::convert).collect(Collectors.toList());
        }

        return new EmployeeDto(employee.getId(), employee.getName(), employee.getSurname(), employee.getTelephone(), addressesDto);
    }
}
