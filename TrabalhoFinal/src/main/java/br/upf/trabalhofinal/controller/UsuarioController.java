package br.upf.trabalhofinal.controller;

import br.upf.trabalhofinal.entity.UsuarioEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinal.facade.UsuarioFacade ejbFacade;
    
    public UsuarioController() {
    }
    
    private UsuarioEntity usuario = new UsuarioEntity();
    
    private UsuarioEntity selected;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<UsuarioEntity> getUsuarioList() {
        return ejbFacade.buscarTodos();
    }

    public void setUsuarioList(List<UsuarioEntity> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public UsuarioEntity getSelected() {
        return selected;
    }

    public void setSelected(UsuarioEntity selected) {
        this.selected = selected;
    }
    
    private List<UsuarioEntity> usuarioList = new ArrayList<>();
    
    public UsuarioEntity prepareAdicionar() {
        usuario = new UsuarioEntity();
        return usuario;
    }
    
    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(usuario);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }
    
    public void adicionarUsuario() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        usuario.setDatahorareg(datahoraAtual);
        persist(UsuarioController.PersistAction.CREATE,
                "Registro incluído com sucesso!"
        );
    }
    
    public void editarUsuario() {
        persist(UsuarioController.PersistAction.UPDATE,
                "Registro alterado com sucesso!"
        );
    }
    
    public void deletarUsuario() {
        persist(UsuarioController.PersistAction.DELETE,
                "Registro excluído com sucesso!"
        );
    }
    
}
