package com.datacrawling.kraken.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Martin Braun
 * @version 1.1.0
 * @since 1.1.0
 */
@Data
@Builder
public class Car {

	private Integer id;
	private String name;

}
