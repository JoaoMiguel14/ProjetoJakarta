package br.upf.trabalhofinal.controller;

import br.upf.trabalhofinal.entity.UsuarioEntity;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinal.facade.UsuarioFacade ejbFacade;

    public LoginController() {
    }
    
    private UsuarioEntity usuario; 

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    public void prepareAutenticarUsuario() {
        usuario = new UsuarioEntity();
    }
    
    @PostConstruct
    public void init() {
        prepareAutenticarUsuario();
    }
    
    public String validarLogin() {
        UsuarioEntity usuarioDB = ejbFacade.buscarPorEmail(usuario.getEmail(), usuario.getSenha());
        if((usuarioDB != null && usuarioDB.getId() != null)) {
            return "/usuarios.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Email ou senha incorreto!"
            );
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;     
        }
    }
    
}
