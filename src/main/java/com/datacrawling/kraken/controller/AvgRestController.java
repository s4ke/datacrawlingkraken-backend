package com.datacrawling.kraken.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.datacrawling.kraken.model.Vehicle;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.datacrawling.kraken.jooq.domain.tables.Readingaverage.READINGAVERAGE;

@RestController
public class AvgRestController {

	@Autowired
	private DSLContext dsl;

	@GetMapping(value = "/avg/vehicles/all")
	public List<Vehicle> getAllVehicles() {
		List<Vehicle> vehicles =
				dsl.selectDistinct( READINGAVERAGE.VEHICLE_NUMBER )
						.from( READINGAVERAGE )
						.stream()
						.map( r -> r.get( 0, String.class ) )
						.map(
								str ->
										Vehicle.builder()
												.vehicleName( str )
												.build() )
						.collect( Collectors.toList() );
		return vehicles;
	}
}
