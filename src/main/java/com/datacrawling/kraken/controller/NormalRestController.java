package com.datacrawling.kraken.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.datacrawling.kraken.model.InjectionData;
import com.datacrawling.kraken.model.Vehicle;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.datacrawling.kraken.jooq.domain.tables.Reading.READING;

/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
@RestController
@CrossOrigin
public class NormalRestController {

	@Autowired
	private DSLContext dsl;

	@GetMapping(value = "/normal/vehicles/all/list")
	public List<Vehicle> getAllVehicles() {
		return dsl.selectDistinct( READING.VEHICLE_NUMBER )
				.from( READING )
				.stream()
				.map( r -> r.get( 0, String.class ) )
				.map(
						str ->
								Vehicle.builder()
										.vehicleName( str )
										.build() )
				.collect( Collectors.toList() );
	}

	@GetMapping(value = "/normal/vehicles/all/count")
	public Integer getVehicleCount() {
		return dsl.select( READING.VEHICLE_NUMBER.countDistinct() )
				.from( READING )
				.fetchOne()
				.get( 0, Integer.class );
	}

	@GetMapping(value = "/normal/readings/count")
	public Integer getReadingsCount() {
		return dsl.selectCount()
				.from( READING )
				.fetchOne()
				.get( 0, Integer.class );
	}

	@GetMapping(value = "/normal/readings/injection/all")
	public List<InjectionData> getAllAverageInjectionReadings() {
		return dsl.select( READING.ID )
				.from( READING )
				.stream()
				.map( r -> {
					return InjectionData.builder()
							.id( r.get( 0, Long.class ) )
							.build();
				} )
				.collect( Collectors.toList() );
	}

}
