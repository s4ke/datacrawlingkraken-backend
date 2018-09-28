package com.datacrawling.kraken.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.datacrawling.kraken.model.Car;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.datacrawling.kraken.jooq.domain.tables.Car.CAR;


/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
@CrossOrigin
@RestController
public class CarController {

	@Autowired
	private DSLContext dsl;

	@GetMapping("/car/all")
	public List<Car> getAllCars() {
		return dsl.select( CAR.ID, CAR.NAME, CAR.DRIVING )
				.from( CAR )
				//hack, so that the actual cars are listed before the auxilliary cars generated
				//by genCars
				.orderBy( CAR.NAME.desc() )
				.stream()
				.map( r -> Car.builder()
						.id( r.get( CAR.ID ) )
						.name( r.get( CAR.NAME ) )
						.driving( r.get( CAR.DRIVING ) != null && r.get( CAR.DRIVING ).equals( (byte) 1 ) )
						.build()
				).collect( Collectors.toList() );
	}

	@GetMapping("/car/count")
	public Integer getCarCount() {
		return dsl.selectCount()
				.from( CAR )
				.fetchOne()
				.get( 0, Integer.class );
	}

	@GetMapping("/car/create/some")
	public String genCars(@RequestParam("count") int count) {
		dsl.transaction( conf -> {
			int curCount = getCarCount();
			for ( int i = 0; i < count; ++i ) {
				dsl.insertInto( CAR, CAR.NAME )
						.values( "Car-" + (curCount + i) ).execute();
			}
		} );
		return "OK";
	}

	@GetMapping("/car/driving/start")
	public String startDriving(@RequestParam("id") int id) {
		dsl.update( CAR )
				.set( CAR.DRIVING, (byte) 1 )
				.where( CAR.ID.eq( id ) ).execute();
		return "OK";
	}

	@GetMapping("/car/driving/startByName")
	public String startDrivingByName(@RequestParam("name") String name) {
		dsl.update( CAR )
				.set( CAR.DRIVING, (byte) 1 )
				.where( CAR.NAME.eq( name ) ).execute();
		return "OK";
	}

	@GetMapping("/car/driving/stop")
	public String stopDriving(@RequestParam("id") int id) {
		dsl.update( CAR )
				.set( CAR.DRIVING, (byte) 0 )
				.where( CAR.ID.eq( id ) ).execute();
		return "OK";
	}

	@GetMapping("/car/driving/stopByName")
	public String stopDrivingByName(@RequestParam("name") String name) {
		dsl.update( CAR )
				.set( CAR.DRIVING, (byte) 0 )
				.where( CAR.NAME.eq( name ) ).execute();
		return "OK";
	}

	@GetMapping("/car/driving/running/count")
	public Integer runningCount() {
		return dsl.selectCount()
				.from( CAR )
				.where( CAR.DRIVING.eq( (byte) 1 ) ).fetchOne().get( 0, Integer.class );
	}

	@GetMapping("/car/driving/stopped/count")
	public Integer stoppedCount() {
		return dsl.selectCount()
				.from( CAR )
				.where( CAR.DRIVING.isNull().or( CAR.DRIVING.eq( (byte) 0 ) ) )
				.fetchOne().get( 0, Integer.class );
	}


}
