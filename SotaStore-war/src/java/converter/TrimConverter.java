/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Duyet
 */
@FacesConverter("converter.TrimConverter")
public class TrimConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        String trimmed = (submittedValue != null) ? submittedValue.trim() : null;
        return (trimmed == null || trimmed.isEmpty()) ? null : trimmed;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        return (modelValue != null) ? modelValue.toString() : "";
    }

}
