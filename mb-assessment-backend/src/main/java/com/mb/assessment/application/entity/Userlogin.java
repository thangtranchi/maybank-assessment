package com.mb.assessment.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQuery(name = "Userlogin.findByUsername",
query = "select u from Userlogin u where u.username = ?1")
public class Userlogin {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;
    

    public Userlogin(Long id, @NotNull String username, @NotNull String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Userlogin() {
        super();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
