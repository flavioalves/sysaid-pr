package br.gov.presidencia.control.bean.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("bigDecimalSimplesConverter")
public class BigDecimalSimplesConverter implements javax.faces.convert.Converter {

	public BigDecimalSimplesConverter() {
	}

	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
			//nf.setMinimumFractionDigits(0);
			//nf.setMaximumFractionDigits(2);
			return new BigDecimal(nf.parse(value).toString());
		} catch (final Exception e) {
			// return 0.0D;
			return null;
		}
	}

	public String getAsString(final FacesContext context, final UIComponent component, Object value) {
		if(value == null) {
			return "";
		}
		final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
		//nf.setMinimumFractionDigits(0);
		//nf.setMaximumFractionDigits(2);
		return nf.format(Double.valueOf(value.toString()));
	}
	
}