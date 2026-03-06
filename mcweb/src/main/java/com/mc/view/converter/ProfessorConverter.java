package com.mc.view.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.mc.model.Professor;
import com.mc.model.dao.ProfessorDao;
import com.mc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Professor.class)
public class ProfessorConverter implements Converter<Object> {

    @Inject
    private ProfessorDao professorDAO;
   
    public ProfessorConverter() {
        this.professorDAO = CDIServiceLocator.getBean(ProfessorDao.class);
    }
   
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Professor retorno = null;

        if (value != null && !value.isEmpty()) {
            retorno = this.professorDAO.buscarPeloCodigo(Long.valueOf(value));
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long id = ((Professor) value).getId();
            String retorno = (id == null ? null : id.toString());
            return retorno;
        }
        return "";
    }

}