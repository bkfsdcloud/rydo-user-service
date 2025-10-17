package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.dto.GMapDto;

import reactor.core.publisher.Mono;

public interface MapService {

	public Mono<String> getRoute(GMapDto gMapDto);
}
