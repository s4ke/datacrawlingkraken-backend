package com.datacrawling.kraken.controller;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.datacrawling.kraken.SpringBootJooqMysqlApplication;
import com.datacrawling.kraken.model.InjectionData;
import com.datacrawling.kraken.model.Vehicle;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(value = "/normal/readings/injection/live")
	public InjectionData getLiveData(
			@RequestParam("vehicleNumber") String vehicleNumber,
			@RequestParam("lastId") Long lastId) {
		Instant now = Instant.now();
		long minTimeStamp = 1533510187;

		long nowEpoch = now.getEpochSecond();
		long nowMilli = now.get( ChronoField.MILLI_OF_SECOND );
		long startEpoch = SpringBootJooqMysqlApplication.startTime.getEpochSecond();
		long startMilli = SpringBootJooqMysqlApplication.startTime.get( ChronoField.MILLI_OF_SECOND );

		long curTimeStamp = minTimeStamp + (nowEpoch - startEpoch);
		//not completely correct, but nvm
		float curMilli = nowMilli - startMilli;
		Result<Record> rec = dsl.select(
				READING.ID,
				READING.VEHICLE_NUMBER,
				READING.FUEL_PRESSURE,
				READING.FUEL_TEMPERATURE_BEFORE_PUMP,
				READING.FUEL_TEMPERATURE_AT_PUMP_OUTLET,
				READING.FUEL_TEMPERATURE_IN_RAIL,
				READING.VEHICLE_SPEED,
				READING.ENGINE_SPEED,
				READING.ENGINE_MAF,
				READING.PEDAL_POSITION,
				READING.AMBIANT_PRESSURE,
				READING.AMBIANT_TEMPERATURE,
				READING.DISTANCE_KM,
				READING.INJECTION_NUMBER,
				READING.INJECTOR_ADAPATION1,
				READING.INJECTOR_ADAPATION2,
				READING.INJECTOR_ADAPATION3,
				READING.INJECTOR_ADAPATION4,
				READING.INJECTOR_ADAPATION5,
				READING.INJECTOR_ADAPATION6,
				READING.INJECTOR_ADAPATION7,
				READING.INJECTOR_ADAPATION8,
				READING.INJECTOR_ADAPATION9,
				READING.INJECTOR_ADAPATION10,
				READING.INJECTOR_ADAPATION11,
				READING.INJECTOR_ADAPATION12,
				READING.INJECTOR_ADAPATION13,
				READING.INJECTOR_ADAPATION14,
				READING.INJECTOR_ADAPATION15,
				READING.INJECTOR_ADAPATION16,
				READING.INJECTOR_ADAPATION17,
				READING.INJECTOR_ADAPATION18,
				READING.INJECTOR_ADAPATION19,
				READING.INJECTOR_ADAPATION20
		)
				.from( READING )
				.where( READING.VEHICLE_NUMBER.eq( vehicleNumber )
								.and( READING.ID.gt( lastId ) ) )
				//FIXME: in reality we would want to sort desc()
				//but as we want to have this as a "replay" feature, asc() is required
				.orderBy( READING.ID.asc() )
				.limit( 1 )
				.fetch();
		if ( rec.size() > 0 ) {
			return rec.stream().limit( 1 )
					.map(
							r -> {
								InjectionData.InjectionDataBuilder builder = InjectionData.builder()
										.id( r.get( READING.ID ) )
										.vehicleNumber( r.get( READING.VEHICLE_NUMBER ) )
										.fuelPressure( r.get( READING.FUEL_PRESSURE ).floatValue() )
										.fuelTemperatureBeforePump( r.get( READING.FUEL_TEMPERATURE_BEFORE_PUMP )
																			.floatValue() )
										.fuelTemperatureAtPumpOutlet( r.get( READING.FUEL_TEMPERATURE_AT_PUMP_OUTLET )
																			  .floatValue() )
										.fuelTemperatureInRail( r.get( READING.FUEL_TEMPERATURE_IN_RAIL )
																		.floatValue() )
										.vehicleSpeed( r.get( READING.VEHICLE_SPEED ).floatValue() )
										.engineSpeed( r.get( READING.ENGINE_SPEED ).floatValue() )
										.engineMAF( r.get( READING.ENGINE_MAF ).floatValue() )
										.pedalPosition( r.get( READING.PEDAL_POSITION ).floatValue() )
										.ambientPressure( r.get( READING.AMBIANT_PRESSURE ).floatValue() )
										.ambientTemperature( r.get( READING.AMBIANT_TEMPERATURE ).floatValue() )
										.distanceKM( r.get( READING.DISTANCE_KM ).floatValue() )
										.injectionNumber( r.get( READING.INJECTION_NUMBER ).intValue() );

								Integer injectionNumber = r.get( READING.INJECTION_NUMBER ).intValue();

								int offset = 14;
								int cycleCount = 4;
								Map<Integer, List<Float>> injectorAdapter = new HashMap<>();

								for ( int pulse = 0; pulse < injectionNumber; ++pulse ) {
									List<Float> list = new ArrayList<>();
									injectorAdapter.put( pulse, list );
									for ( int cycle = 0; cycle < cycleCount; ++cycle ) {
										list.add(
												r.get(
														offset + cycle * cycleCount + pulse,
														Float.class
												) );
									}
								}
								builder.injectionAdaption( injectorAdapter );
								return builder.build();
							} ).collect( Collectors.toList() ).get( 0 );
		}
		else {
			return null;
		}
	}

}
