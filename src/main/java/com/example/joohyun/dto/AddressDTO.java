package com.example.joohyun.dto;

import com.example.joohyun.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long addrDTOId;
    private String shippingFirstName;
    private String shippingLastName;
    private String shippingCompany;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingCity;
    private String shippingState;
    private String shippingPostcode;
    private String shippingCountry;
    private boolean isDefault;

    public Address toEntity() {
        Address address = new Address();
        address.setShippingFirstName(this.shippingFirstName);
        address.setShippingLastName(this.shippingLastName);
        address.setShippingCompany(this.shippingCompany);
        address.setShippingAddress1(this.shippingAddress1);
        address.setShippingAddress2(this.shippingAddress2);
        address.setShippingCity(this.shippingCity);
        address.setShippingState(this.shippingState);
        address.setShippingPostcode(this.shippingPostcode);
        address.setShippingCountry(this.shippingCountry);
        address.setDefault(this.isDefault);
        return address;
    }

    public static AddressDTO fromEntity(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO dto = new AddressDTO();
        dto.setAddrDTOId(address.getId());
        dto.setShippingFirstName(address.getShippingFirstName());
        dto.setShippingLastName(address.getShippingLastName());
        dto.setShippingCompany(address.getShippingCompany());
        dto.setShippingAddress1(address.getShippingAddress1());
        dto.setShippingAddress2(address.getShippingAddress2());
        dto.setShippingCity(address.getShippingCity());
        dto.setShippingState(address.getShippingState());
        dto.setShippingPostcode(address.getShippingPostcode());
        dto.setShippingCountry(address.getShippingCountry());
        dto.setDefault(address.isDefault());
        return dto;
    }
} 