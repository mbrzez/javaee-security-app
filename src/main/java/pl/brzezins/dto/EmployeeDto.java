package pl.brzezins.dto;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDto implements Dto {
    private Long id;
    private String name;
    private String surname;
    private String telephone;
    private List<AddressDto> addresses = new ArrayList<>();

    public EmployeeDto(Long id, String name, String surname, String telephone, List<AddressDto> addresses) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }
}