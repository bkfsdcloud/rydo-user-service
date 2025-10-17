package com.adroitfirm.rydo.user.mapper;

import com.adroitfirm.rydo.user.dto.PaymentDto;
import com.adroitfirm.rydo.user.entity.Payment;
import com.adroitfirm.rydo.user.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "ride.id", target = "rideId")
    PaymentDto toDto(Payment entity);

    @Mapping(source = "rideId", target = "ride", qualifiedByName = "mapRide")
    Payment toEntity(PaymentDto dto);

    @Named("mapRide")
    default Ride mapRide(Long id) {
        if (id == null) return null;
        Ride r = new Ride();
        r.setId(id);
        return r;
    }
}