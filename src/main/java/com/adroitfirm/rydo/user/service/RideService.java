package com.adroitfirm.rydo.user.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.dto.RideDto;
import com.adroitfirm.rydo.model.kafka.RideRequested;
import com.adroitfirm.rydo.user.entity.Ride;
import com.adroitfirm.rydo.user.enumeration.RideStatus;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.mapper.RideMapper;
import com.adroitfirm.rydo.user.repository.RideRepository;
import com.adroitfirm.rydo.user.util.RideConstants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RideService {
    private final RideRepository repo;
    private final RideMapper mapper;
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public RideDto create(RideDto r) {
    	Ride ride = repo.save(mapper.toEntity(r));
    	RideDto rideDto = mapper.toDto(ride);
    	
    	RideRequested rideRequested = RideRequested.builder()
				.rideId(rideDto.getId())
				.status(RideStatus.REQUESTED.name())
				.userId(rideDto.getCustomerId())
				.createdAt(Instant.now())
				.build();
		
		kafkaTemplate.send(RideConstants.RIDE_REQUESTED_TOPIC, rideRequested);
    	
    	return rideDto;
    }
    
    public String event(RideDto rideDto, RideStatus status) {
    	RideRequested rideRequested = null;
    	switch (status) {
    		case CANCELLED:
    			rideRequested = RideRequested.builder()
				.rideId(rideDto.getId())
				.status(status.name())
				.build();
    			kafkaTemplate.send(RideConstants.RIDE_CANCELLED_TOPIC, rideRequested);
    			return "Cancelled";
			case ACCEPTED:
				rideRequested = RideRequested.builder()
				.driverId(rideDto.getDriverId())
				.rideId(rideDto.getId())
				.status(status.name())
				.build();
    			kafkaTemplate.send(RideConstants.RIDE_ACCEPTED_TOPIC, rideRequested);
    			return "Accepted";
			case COMPLETED:
				rideRequested = RideRequested.builder()
				.driverId(rideDto.getDriverId())
				.rideId(rideDto.getId())
				.status(status.name())
				.build();
    			kafkaTemplate.send(RideConstants.RIDE_COMPLETED_TOPIC, rideRequested);
    			return "Completed";
			case DENIED:
				rideRequested = RideRequested.builder()
				.driverId(rideDto.getDriverId())
				.rideId(rideDto.getId())
				.status(status.name())
				.build();
    			kafkaTemplate.send(RideConstants.RIDE_DENIED_TOPIC, rideRequested);
    			return "Denied";
			default:
				break;
    	}
    	return null;
    	
    }
    
    public List<RideDto> findAll() {
    	List<RideDto> dtos = repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    	return dtos; 
    }
    public RideDto findById(Long id) { 
    	Ride ride = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ride not found: " + id));
    	return mapper.toDto(ride); 
    }
}