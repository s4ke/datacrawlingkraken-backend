package com.datacrawling.kraken.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.datacrawling.kraken.model.InjectionData;
import com.datacrawling.kraken.model.Vehicle;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.datacrawling.kraken.jooq.domain.tables.Readingaverage.READINGAVERAGE;

@RestController
@CrossOrigin
public class AvgRestController {

	@Autowired
	private DSLContext dsl;

	@GetMapping(value = "/avg/vehicles/all/list")
	public List<Vehicle> getAllVehicles() {
		return dsl.selectDistinct( READINGAVERAGE.VEHICLE_NUMBER )
				.from( READINGAVERAGE )
				.stream()
				.map( r -> r.get( 0, String.class ) )
				.map(
						str ->
								Vehicle.builder()
										.vehicleName( str )
										.build() )
				.collect( Collectors.toList() );
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
		return dsl.select( READINGAVERAGE.ID,
						   READINGAVERAGE.VEHICLE_NUMBER,
						   READINGAVERAGE.FUEL_PRESSURE,
						   READINGAVERAGE.FUEL_TEMPERATURE_BEFORE_PUMP,
						   READINGAVERAGE.FUEL_TEMPERATURE_AT_PUMP_OUTLET,
						   READINGAVERAGE.FUEL_TEMPERATURE_IN_RAIL,
						   READINGAVERAGE.VEHICLE_SPEED,
						   READINGAVERAGE.ENGINE_SPEED,
						   READINGAVERAGE.ENGINE_MAF,
						   READINGAVERAGE.PEDAL_POSITION,
						   READINGAVERAGE.AMBIANT_PRESSURE,
						   READINGAVERAGE.AMBIANT_TEMPERATURE,
						   READINGAVERAGE.DISTANCE_KM,
						   READINGAVERAGE.FUEL_PUMP_DELIVERY,
						   READINGAVERAGE.FUEL_MASS_ADAPATION,
						   READINGAVERAGE.GEAR_ENGAGED,
						   READINGAVERAGE.INJECTION_NUMBER,
						   READINGAVERAGE.INJECTOR_ADAPATION1,
						   READINGAVERAGE.INJECTOR_ADAPATION2,
						   READINGAVERAGE.INJECTOR_ADAPATION3,
						   READINGAVERAGE.INJECTOR_ADAPATION4,
						   READINGAVERAGE.INJECTOR_ADAPATION5,
						   READINGAVERAGE.INJECTOR_ADAPATION6,
						   READINGAVERAGE.INJECTOR_ADAPATION7,
						   READINGAVERAGE.INJECTOR_ADAPATION8,
						   READINGAVERAGE.INJECTOR_ADAPATION9,
						   READINGAVERAGE.INJECTOR_ADAPATION10,
						   READINGAVERAGE.INJECTOR_ADAPATION11,
						   READINGAVERAGE.INJECTOR_ADAPATION12,
						   READINGAVERAGE.INJECTOR_ADAPATION13,
						   READINGAVERAGE.INJECTOR_ADAPATION14,
						   READINGAVERAGE.INJECTOR_ADAPATION15,
						   READINGAVERAGE.INJECTOR_ADAPATION16,
						   READINGAVERAGE.INJECTOR_ADAPATION17,
						   READINGAVERAGE.INJECTOR_ADAPATION18,
						   READINGAVERAGE.INJECTOR_ADAPATION19,
						   READINGAVERAGE.INJECTOR_ADAPATION20 )
				.from( READINGAVERAGE )
				.stream()
				//FIXME: COPY PASTA
				.map( r -> {
					InjectionData.InjectionDataBuilder builder = InjectionData.builder()
							.id( r.get( READINGAVERAGE.ID ) )
							.vehicleNumber( r.get( READINGAVERAGE.VEHICLE_NUMBER ) )
							.fuelPressure( r.get( READINGAVERAGE.FUEL_PRESSURE ).floatValue() )
							.fuelTemperatureBeforePump( r.get( READINGAVERAGE.FUEL_TEMPERATURE_BEFORE_PUMP )
																.floatValue() )
							.fuelTemperatureAtPumpOutlet( r.get( READINGAVERAGE.FUEL_TEMPERATURE_AT_PUMP_OUTLET )
																  .floatValue() )
							.fuelTemperatureInRail( r.get( READINGAVERAGE.FUEL_TEMPERATURE_IN_RAIL ).floatValue() )
							.vehicleSpeed( r.get( READINGAVERAGE.VEHICLE_SPEED ).floatValue() )
							.engineSpeed( r.get( READINGAVERAGE.ENGINE_SPEED ).floatValue() )
							.engineMAF( r.get( READINGAVERAGE.ENGINE_MAF ).floatValue() )
							.pedalPosition( r.get( READINGAVERAGE.PEDAL_POSITION ).floatValue() )
							.ambientPressure( r.get( READINGAVERAGE.AMBIANT_PRESSURE ).floatValue() )
							.ambientTemperature( r.get( READINGAVERAGE.AMBIANT_TEMPERATURE ).floatValue() )
							.distanceKM( r.get( READINGAVERAGE.DISTANCE_KM ).floatValue() )
							.injectionNumber( r.get( READINGAVERAGE.INJECTION_NUMBER ).intValue() )
							.pumpSpeed( r.get(READINGAVERAGE.FUEL_PUMP_DELIVERY).floatValue() )
							.fuelMassDelivery( r.get(READINGAVERAGE.FUEL_MASS_ADAPATION).floatValue() )
							.gearEngaged( r.get(READINGAVERAGE.GEAR_ENGAGED).floatValue() );

					Integer injectionNumber = r.get( READINGAVERAGE.INJECTION_NUMBER ).intValue();

					int offset = 17;
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
				} )
				.collect( Collectors.toList() );
	}

	/*
	 * FIXME: BETTER: https://www.jooq.org/doc/3.11/manual/sql-execution/fetching/pojos/
	 */
	@GetMapping(value = "/avg/readings/injection/car")
	public List<InjectionData> getAverageInjectionReadings(
			//FIXME: we ignore this for now, quick fix so that we always have data available
			//in the presentation
			@RequestParam("vehicleNumber") String vehicleNumber,
			@RequestParam("lowerKM") Optional<Float> lowerKM,
			@RequestParam("upperKM") Optional<Float> upperKM
	) {
		SelectJoinStep<Record> selectJoinStep =
				dsl.select(
						READINGAVERAGE.ID,
						READINGAVERAGE.VEHICLE_NUMBER,
						READINGAVERAGE.FUEL_PRESSURE,
						READINGAVERAGE.FUEL_TEMPERATURE_BEFORE_PUMP,
						READINGAVERAGE.FUEL_TEMPERATURE_AT_PUMP_OUTLET,
						READINGAVERAGE.FUEL_TEMPERATURE_IN_RAIL,
						READINGAVERAGE.VEHICLE_SPEED,
						READINGAVERAGE.ENGINE_SPEED,
						READINGAVERAGE.ENGINE_MAF,
						READINGAVERAGE.PEDAL_POSITION,
						READINGAVERAGE.AMBIANT_PRESSURE,
						READINGAVERAGE.AMBIANT_TEMPERATURE,
						READINGAVERAGE.DISTANCE_KM,
						READINGAVERAGE.FUEL_PUMP_DELIVERY,
						READINGAVERAGE.FUEL_MASS_ADAPATION,
						READINGAVERAGE.GEAR_ENGAGED,
						READINGAVERAGE.INJECTION_NUMBER,
						READINGAVERAGE.INJECTOR_ADAPATION1,
						READINGAVERAGE.INJECTOR_ADAPATION2,
						READINGAVERAGE.INJECTOR_ADAPATION3,
						READINGAVERAGE.INJECTOR_ADAPATION4,
						READINGAVERAGE.INJECTOR_ADAPATION5,
						READINGAVERAGE.INJECTOR_ADAPATION6,
						READINGAVERAGE.INJECTOR_ADAPATION7,
						READINGAVERAGE.INJECTOR_ADAPATION8,
						READINGAVERAGE.INJECTOR_ADAPATION9,
						READINGAVERAGE.INJECTOR_ADAPATION10,
						READINGAVERAGE.INJECTOR_ADAPATION11,
						READINGAVERAGE.INJECTOR_ADAPATION12,
						READINGAVERAGE.INJECTOR_ADAPATION13,
						READINGAVERAGE.INJECTOR_ADAPATION14,
						READINGAVERAGE.INJECTOR_ADAPATION15,
						READINGAVERAGE.INJECTOR_ADAPATION16,
						READINGAVERAGE.INJECTOR_ADAPATION17,
						READINGAVERAGE.INJECTOR_ADAPATION18,
						READINGAVERAGE.INJECTOR_ADAPATION19,
						READINGAVERAGE.INJECTOR_ADAPATION20
				).from( READINGAVERAGE );
		Condition condition = READINGAVERAGE.ID.isNotNull();
		if ( lowerKM.isPresent() ) {
			condition = condition.and( READINGAVERAGE.DISTANCE_KM.
					ge( lowerKM.get().doubleValue() )
			);
		}
		if ( upperKM.isPresent() ) {
			condition = condition.and( READINGAVERAGE.DISTANCE_KM.
					le( upperKM.get().doubleValue() )
			);
		}
		Stream<Record> result = selectJoinStep.where( condition )
				.orderBy( READINGAVERAGE.DISTANCE_KM ).stream();
		return result.map(
				r -> {
					InjectionData.InjectionDataBuilder builder = InjectionData.builder()
							.id( r.get( READINGAVERAGE.ID ) )
							.vehicleNumber( r.get( READINGAVERAGE.VEHICLE_NUMBER ) )
							.fuelPressure( r.get( READINGAVERAGE.FUEL_PRESSURE ).floatValue() )
							.fuelTemperatureBeforePump( r.get( READINGAVERAGE.FUEL_TEMPERATURE_BEFORE_PUMP )
																.floatValue() )
							.fuelTemperatureAtPumpOutlet( r.get( READINGAVERAGE.FUEL_TEMPERATURE_AT_PUMP_OUTLET )
																  .floatValue() )
							.fuelTemperatureInRail( r.get( READINGAVERAGE.FUEL_TEMPERATURE_IN_RAIL ).floatValue() )
							.vehicleSpeed( r.get( READINGAVERAGE.VEHICLE_SPEED ).floatValue() )
							.engineSpeed( r.get( READINGAVERAGE.ENGINE_SPEED ).floatValue() )
							.engineMAF( r.get( READINGAVERAGE.ENGINE_MAF ).floatValue() )
							.pedalPosition( r.get( READINGAVERAGE.PEDAL_POSITION ).floatValue() )
							.ambientPressure( r.get( READINGAVERAGE.AMBIANT_PRESSURE ).floatValue() )
							.ambientTemperature( r.get( READINGAVERAGE.AMBIANT_TEMPERATURE ).floatValue() )
							.distanceKM( r.get( READINGAVERAGE.DISTANCE_KM ).floatValue() )
							.injectionNumber( r.get( READINGAVERAGE.INJECTION_NUMBER ).intValue() )
							.pumpSpeed( r.get(READINGAVERAGE.FUEL_PUMP_DELIVERY).floatValue() )
							.fuelMassDelivery( r.get(READINGAVERAGE.FUEL_MASS_ADAPATION).floatValue() )
							.gearEngaged( r.get(READINGAVERAGE.GEAR_ENGAGED).floatValue() );

					Integer injectionNumber = r.get( READINGAVERAGE.INJECTION_NUMBER ).intValue();

					int offset = 17;
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
				} ).collect( Collectors.toList() );
	}

}
