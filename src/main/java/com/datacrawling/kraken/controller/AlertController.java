package com.datacrawling.kraken.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.datacrawling.kraken.model.Alert;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.datacrawling.kraken.jooq.domain.tables.Alert.ALERT;

/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
@RestController
@CrossOrigin
public class AlertController {

	@Autowired
	private DSLContext dsl;

	@GetMapping("/alert/all")
	public List<Alert> getAllAlerts(@RequestParam("lastId") Optional<Long> lastId) {
		Condition condition = ALERT.HANDLED.isNull().or( ALERT.HANDLED.ne( (byte) 1 ) );
		if ( lastId.isPresent() ) {
			condition = condition.and( ALERT.ID.gt( lastId.get() ) );
		}
		return dsl.select(
				ALERT.ID,
				ALERT.VEHICLE_NUMBER,
				ALERT.ALERT_TYPE,
				ALERT.HANDLED,
				ALERT.LOWERKM,
				ALERT.UPPERKM
		)
				.from( ALERT )
				.where( condition )
				.orderBy( ALERT.ID )
				.stream()
				.map(
						r -> Alert.builder()
								.id( r.get( ALERT.ID ) )
								.vehicleNumber( r.get( ALERT.VEHICLE_NUMBER ) )
								.type( r.get( ALERT.ALERT_TYPE ) )
								.handled( r.get( ALERT.HANDLED ) != null && r.get( ALERT.HANDLED ).equals( (byte) 1 ) )
								.lowerKM( r.get( ALERT.LOWERKM ) )
								.upperKM( r.get( ALERT.UPPERKM ) )
								.build()
				).collect( Collectors.toList() );
	}

	@GetMapping("/alert/new")
	public String createAlert(
			@RequestParam("vehicleNumber") String vehicleNumber,
			@RequestParam("type") Integer type,
			@RequestParam("lowerKM") Double lowerKM,
			@RequestParam("upperKM") Double upperKM
	) {
		dsl.insertInto(
				ALERT,
				ALERT.VEHICLE_NUMBER,
				ALERT.ALERT_TYPE,
				ALERT.LOWERKM,
				ALERT.UPPERKM
		)
				.values(
						vehicleNumber,
						type,
						lowerKM,
						upperKM
				).execute();
		//normally, this would be a post, but this allows debugging in the browser
		return "OK";
	}

	@GetMapping("/alert/handle")
	public String createAlert(@RequestParam("id") Long id) {
		dsl.update( ALERT )
				.set( ALERT.HANDLED, (byte) 1 )
				.where( ALERT.ID.eq( id ) ).execute();
		//normally, this would be a post, but this allows debugging in the browser
		return "OK";
	}

}
