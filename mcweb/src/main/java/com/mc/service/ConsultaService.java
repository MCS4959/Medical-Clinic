package com.mc.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import com.mc.model.Consulta;
import com.mc.model.dao.ConsultaDao;

public class ConsultaService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject	
	private ConsultaDao consultaDao;
	
	public void salvar(Consulta consulta) {
		consultaDao.salvar(consulta);
	}
	
	public void excluir(Consulta consulta) {
		this.consultaDao.excluir(consulta);
	}

	public List<Consulta> buscarTodos() {
		
		return consultaDao.buscarTodos();
	}
}