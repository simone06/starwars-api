package com.b2w.starwars.api.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document
public class Planeta {
	@Id
	public String id;
	
	@Indexed(unique = true)
	
	@NotNull
	private String nome;
	
	@NotNull
	private String clima;
	
	@NotNull
	private String terreno;

	
	@Transient
	private Integer qtdAparicoesEmFilme;

	public Planeta(@NotNull String nome, @NotNull String clima, @NotNull String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}

	public Planeta() {
		super();
		// TODO Auto-generated constructor stub
	} 
}
