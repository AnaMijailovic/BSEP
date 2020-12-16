package com.defencefirst.siemcentar.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("operator")
public class Operator extends User {
	private static final long serialVersionUID = 1L;

	public Operator() {
		super();
	}



}
