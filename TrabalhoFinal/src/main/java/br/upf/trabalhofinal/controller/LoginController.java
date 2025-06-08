package br.upf.trabalhofinal.controller;

import br.upf.trabalhofinal.entity.UsuarioEntity;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.IOException;

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
            this.usuario = usuarioDB;
            
            switch (usuario.getPermissao()) {
            case "Admin":
                return "/usuarios.xhtml?faces-redirect=true";
            case "RH":
                return "/funcionarios.xhtml?faces-redirect=true";
            case "Comercial":
                return "/fornecedores.xhtml?faces-redirect=true";
            case "Loja":
                return "/produtos.xhtml?faces-redirect=true";
            default:
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Permissão desconhecida!",
                        "Sua conta possui uma permissão que não está configurada para redirecionamento."));
                return null;
            }
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
    
    public String acessar(String pagina) {
        if (verificarPermissao(pagina)) {
            return pagina + ".xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Acesso negado!", "Você não tem permissão para acessar esta página."));
            return null;
        }
    }

    public boolean verificarPermissao(String pagina) {
        if (usuario == null) return false;
    
        switch (usuario.getPermissao()) {
            case "Admin":
                return true;
            case "RH":
                return pagina.equals("funcionarios");
            case "Comercial":
                return pagina.equals("produtos") || pagina.equals("fornecedores");
            case "Loja":
                return pagina.equals("produtos");
            default:
                return false;
        }
    }
    
}
