package com.mc.view.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.mc.model.Curso;
import com.mc.model.dao.CursoDao;
import com.mc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Curso.class)
public class CursoConverter implements Converter<Object> {

    @Inject
    private CursoDao cursoDAO;
   
    public CursoConverter() {
        this.cursoDAO = CDIServiceLocator.getBean(CursoDao.class);
    }
   
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Curso retorno = null;

        if (value != null && !value.isEmpty()) {
            retorno = this.cursoDAO.buscarPeloCodigo(Long.valueOf(value));
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long id = ((Curso) value).getId();
            String retorno = (id == null ? null : id.toString());
            return retorno;
        }
        return "";
    }

}