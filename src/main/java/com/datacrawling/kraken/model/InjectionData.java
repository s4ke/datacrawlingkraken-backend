package com.datacrawling.kraken.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
@Data
@Builder
public class InjectionData {

	private Long id;

	private String vehicleNumber;

	private Float fuelPressure;
	private Float fuelTemperatureBeforePump;
	private Float fuelTemperatureAtPumpOutlet;

	private Float fuelTemperatureInRail;

	private Integer injectionNumber;
	private Map<Integer, List<Float>> injectionAdaption;

	private Float vehicleSpeed;
	private Float engineSpeed;
	private Float engineMAF;

	private Float pedalPosition;

	private Float ambientPressure;
	private Float ambientTemperature;

	private Float distanceKM;

}
