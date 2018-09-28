package com.datacrawling.kraken.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.datacrawling.kraken.model.InjectionData;
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

	@GetMapping(value = "/avg/vehicles/all/list")
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

	@GetMapping(value = "/avg/vehicles/all/count")
	public Integer getVehicleCount() {
		return dsl.select( READINGAVERAGE.VEHICLE_NUMBER.countDistinct() )
				.from( READINGAVERAGE )
				.fetchOne()
				.get( 0, Integer.class );
	}

	@GetMapping(value = "/avg/readings/count")
	public Integer getReadingsCount() {
		return dsl.selectCount()
				.from( READINGAVERAGE )
				.fetchOne()
				.get( 0, Integer.class );
	}

	@GetMapping(value = "/avg/readings/injection/all")
	public List<InjectionData> getAllAverageInjectionReadings() {
		return dsl.select( READINGAVERAGE.ID )
				.from( READINGAVERAGE )
				.stream()
				.map( r -> {
					return InjectionData.builder()
							.id( r.get( 0, Long.class ) )
							.build();
				} )
				.collect( Collectors.toList() );
	}

}
