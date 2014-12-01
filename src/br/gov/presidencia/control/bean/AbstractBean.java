package br.gov.presidencia.control.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.context.RequestContext;

import br.gov.presidencia.facade.UsuarioFacade;
import br.gov.presidencia.model.Usuario;
import br.gov.presidencia.util.JSFMessageUtil;

public abstract class AbstractBean implements Serializable {
	
	
	@Inject
	private UsuarioFacade usuarioFacade;

	public AbstractBean() {
		super();
	}

	protected void displayInfoMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendInfoMessageToUser(message);
	}
	
	protected void displayWarnMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendWarnMessageToUser(message);
	}
	
	protected void displayErrorMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendErrorMessageToUser(message);
	}
	
	protected RequestContext getRequestContext(){
		return RequestContext.getCurrentInstance();
	}
	
	public void geraExtractCSV(String output, String nomeArquivo) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		char[] chCsvData = output.toCharArray();
		response.setHeader("Content-Disposition","attachment;filename="+nomeArquivo+".csv");
		response.setContentType("application/ms-excel");
		response.setIntHeader("Content-length", chCsvData.length);
		inputStream = new ByteArrayInputStream(output.getBytes("ISO-8859-1"));
		outputStream = response.getOutputStream();
		byte buffer[] = new byte[1024 * 1024];
		int value;
		while ((value = inputStream.read(buffer)) != -1){
			outputStream.write(buffer, 0, value);
		}
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void gravarCookie(String username) {
		try {
			username = Base64.encodeBase64String(username.getBytes());
			FacesContext context = FacesContext.getCurrentInstance();
			//Cookie ck = new Cookie("communityUserName", "BASE64c3lzdGVt");
			Cookie ck = new Cookie("communityUserName", "BASE64"+username);
			ck.setMaxAge(-1); // tempo de vida
			((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(ck);
			System.out.println("Cookie salvo...");
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public void removerCookie() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().trim().equalsIgnoreCase("communityUserName")) {
					Cookie ck = new Cookie("communityUserName", "xx");
					ck.setMaxAge(0); // tempo de vida
					((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(ck);
				}
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public  Usuario getUsuarioLogadoCookie() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();	

			Cookie[] cookies = request.getCookies();
			Usuario user = new Usuario();
			
			for (Cookie cookie : cookies) {
				if (cookie.getName().trim().equalsIgnoreCase("communityUserName")) {	
					if(cookie.getValue().length() > 6) {
						String decoded = new String(Base64.decodeBase64(cookie.getValue().substring(6)));
						System.out.println("Encontrou o cookie communityUserName. Valor: " + cookie.getValue() + " Valor Real: " + decoded);
						user = usuarioFacade.find(decoded);					
						return user;
					}
				}
				if (cookie.getName().trim().equalsIgnoreCase("sysaidcookie")) {
					
					System.out.println("Encontrou o cookie sysaidcookie. Valor: " + cookie.getValue());
					String decoded = new String(Base64.decodeBase64(cookie.getValue().substring(6)));
					System.out.println("Encontrou o cookie communityUserName. Valor: " + cookie.getValue() + " Valor Real: " + decoded);
					user = usuarioFacade.find(decoded);					
					return user;
				}
			}
		} catch (Exception x) {
			System.out.println("Erro ao caputurar o usuario pelo cookie. Erro: " + x.getMessage());
			x.printStackTrace();
		}
		System.out.println("Nao encontrou os cookie communityUserName ou sysaidcookie");
	/*	Usuario teste = new Usuario();
		teste.setUserNameCalculado("MR Teste");
		teste.setUserName("teste");*/
		return null;
	}
	
	public  String getDecodedUsuarioCookie() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			Cookie[] cookies = request.getCookies();
			
			for (Cookie cookie : cookies) {
				if (cookie.getName().trim().equalsIgnoreCase("communityUserName")) {								
					String decoded = new String(Base64.decodeBase64(cookie.getValue().substring(6)));					
					return decoded;					
				}
			}

		} catch (Exception x) {
			x.printStackTrace();
		}
		return null;
	}
	
	public void verificarUsuarioLogado() {
		if(getUsuarioLogadoCookie() == null) {
			throw new RuntimeException("� necess�rio est� logado.");
		}
	}
	/**
	 * Não é para abusar
	 * @param chave
	 * @param obj
	 */
	public void saveObjectInSession(String chave, Object obj){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(chave, obj);
	}
	
	public Object getObjectInSession(String chave){
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(chave);
	}
	
	public String getUrlOS(){
		FacesContext context = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = context.getExternalContext();
	    String url = externalContext.getApplicationContextPath();
	    
	    return url.substring(0, url.indexOf(":"));
	}
	
	/*FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = context.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    request.getSession().getServletContext().setAttribute(chave, obj);
	//FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(chave, obj);
	 */
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1682923677414926924L;

}
