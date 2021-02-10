package com.bolsadeideas.springboot.redisdb.app.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@Document(collection = "employee")
public class Employee implements Serializable {
	@Id
	private String id;
	private @NonNull String firstName;
	private @NonNull String lasteName;
	private @NonNull String email;
	
	private static final long serialVersionUID = 2045224503906451815L;
}
