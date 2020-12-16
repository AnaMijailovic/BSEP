package com.defencefirst.siemcentar.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("administrator")
public class Administrator extends User {
	private static final long serialVersionUID = 1L;

	public Administrator() {
		super();
	}

	

}
