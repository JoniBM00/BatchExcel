package com.viewnext.batchexcel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

	private String id;
	private String name;
	private String descripcion;
	private long code;

}
