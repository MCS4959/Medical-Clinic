package com.mc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
public class Aluno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@ToString.Include	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	

	private String prontuario;
	private String nome;
	private boolean ativo;

	private String telefone;
	private Float salario;
	private Date dataNascimento;


	@ManyToOne
	private Professor professor;
	@ManyToOne
	private Curso curso;
}
