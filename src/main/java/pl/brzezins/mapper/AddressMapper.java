package pl.brzezins.mapper;

import pl.brzezins.dto.AddressDto;
import pl.brzezins.entity.Address;

import javax.enterprise.context.Dependent;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
public class AddressMapper implements DtoMapper<AddressDto, Address> {
    @Override
    public AddressDto convert(Address address) {
        AddressDto addressDto = new AddressDto(address.getId(), address.getStreet(), address.getCity(), address.getCityCode(), null);

        return addressDto;
    }
}
