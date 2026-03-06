package com.mc.view.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.mc.model.Disciplina;
import com.mc.model.dao.DisciplinaDao;
import com.mc.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Disciplina.class)
public class DisciplinaConverter implements Converter<Object> {

    @Inject
    private DisciplinaDao disciplinaDAO;
   
    public DisciplinaConverter() {
        this.disciplinaDAO = CDIServiceLocator.getBean(DisciplinaDao.class);
    }
   
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Disciplina retorno = null;

        if (value != null && !value.isEmpty()) {
            retorno = this.disciplinaDAO.buscarPeloCodigo(Long.valueOf(value));
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long id = ((Disciplina) value).getId();
            String retorno = (id == null ? null : id.toString());
            return retorno;
        }
        return "";
    }

}