package com.albo.challengealbomarvel.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
public class GenericApiRsDto<T> {

	private Integer code;
	private String date;
	private String id;
	private T response;

	public GenericApiRsDto() {
		super();
	}

	public GenericApiRsDto(Integer code, T response) {
		super();
		this.code = code;
		this.response = response;

		// Calculamos la fecha de la trx.
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.date = format.format(new Date());

		// Calculamos el ID de la trx.
		this.id = "Id-" + UUID.randomUUID().toString();
	}

	public GenericApiRsDto(T response) {
		this(0, response);
	}
}
