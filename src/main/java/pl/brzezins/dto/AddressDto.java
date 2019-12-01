package pl.brzezins.dto;

public class AddressDto implements Dto {
    private Long id;
    private String street;
    private String city;
    private String cityCode;
    private EmployeeDto employee;

    public AddressDto() { }

    public AddressDto(String street, String city, String cityCode, EmployeeDto employee) {
        this.street = street;
        this.city = city;
        this.cityCode = cityCode;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }
}
