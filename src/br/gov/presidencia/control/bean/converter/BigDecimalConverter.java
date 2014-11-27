package br.gov.presidencia.control.bean.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements javax.faces.convert.Converter {

	public BigDecimalConverter() {
	}

	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			return new BigDecimal(nf.parse(value).toString()).setScale(2);
		} catch (final Exception e) {
			// return 0.0D;
			return null;
		}
	}

	public String getAsString(final FacesContext context, final UIComponent component, Object value) {
		if ((value == null) || (value.toString().trim().equals(""))) {
			value = 0.0D;
		}
		final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf.format(Double.valueOf(value.toString()));
	}
	
}