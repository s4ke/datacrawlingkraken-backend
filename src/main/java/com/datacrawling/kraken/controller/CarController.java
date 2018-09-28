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
		return dsl.select( CAR.ID, CAR.NAME )
				.from( CAR )
				//hack, so that the actual cars are listed before the auxilliary cars generated
				//by genCars
				.orderBy( CAR.NAME.desc() )
				.stream()
				.map( r -> Car.builder()
						.id( r.get( CAR.ID ) )
						.name( r.get( CAR.NAME ) )
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
		return "";
	}

}