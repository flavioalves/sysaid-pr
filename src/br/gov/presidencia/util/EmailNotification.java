package br.gov.presidencia.util;

import java.text.SimpleDateFormat;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.gov.presidencia.model.Indisponibilidade;
import br.gov.presidencia.model.Usuario;

public class EmailNotification {
	
	public boolean envarEmailUsuario(Usuario user, Usuario tecnico, Indisponibilidade indisp) throws EmailException{
		
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		
		Email email = new SimpleEmail();
		email.setHostName("10.1.2.150");
		email.setSmtpPort(465);
		//email.setAuthenticator(new DefaultAuthenticator("contato@saginformatica.com.br", "#"));
		//email.setSSL(true);
		email.setFrom("centraldeservicos@planalto.gov.br");
		email.setSubject("Notificação de Cadastro de Indisponibilidade de Técnico");
		email.setMsg("Aviso de de Indisponibilidade do(a) Técnico(a): "+ tecnico.getUserNameCalculado() +" no periodo de "
		+sdf.format(indisp.getInicio()) +" a "+ sdf.format(indisp.getFim()) +" Motivo: "+indisp.getTipo().getNome() );
		if(user.getEmail() != null){
			email.addTo(user.getEmail());
			email.send();
		}
		
		//email.addTo("alvaroqv@gmail.com");
		
		
		return false;
	}

}
