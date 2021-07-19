package com.test.profileapp.rest.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class Person {

	@ApiModelProperty(hidden = true)
	private Integer id;

	@NotBlank(message = "First Name is mandatory")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	private String lastName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstNAme) {
		this.firstName = firstNAme;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
