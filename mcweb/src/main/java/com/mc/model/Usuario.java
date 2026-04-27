package com.mc.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mc.model.enums.Especialidade;
import com.mc.model.enums.Perfil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
public class Usuario implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@ToString.Include	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String email;
    private String senha;
    @ToString.Include	
    private String nome;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private String crm;
    
}
