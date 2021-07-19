package com.test.profileapp.rest.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class Address {

	@ApiModelProperty(hidden = true)
	private int id;

	@NotBlank(message = "Address:Street is mandatory")
	private String street;

	@NotBlank(message = "Address:City is mandatory")
	private String city;

	@NotBlank(message = "ADdress:State is mandatory")
	private String state;

	@NotBlank(message = "Address:Postalcode is mandatory")
	private String postalCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
