package br.upf.trabalhofinal.controller;

import br.upf.trabalhofinal.entity.FuncionarioEntity;
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

@Named(value = "funcionarioController")
@SessionScoped
public class FuncionarioController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinal.facade.FuncionarioFacade ejbFacade;

    public FuncionarioController() {
    }
    
    private FuncionarioEntity funcionario = new FuncionarioEntity();
    
    private FuncionarioEntity selected;
    
    public FuncionarioEntity getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioEntity funcionario) {
        this.funcionario = funcionario;
    }
    
    public List<FuncionarioEntity> getFuncionarioList() {
        return ejbFacade.buscarTodos();
    }
    
    public void setFuncionarioList(List<FuncionarioEntity> funcionarioList) {
        this.funcionarioList = funcionarioList;
    }

    public FuncionarioEntity getSelected() {
        return selected;
    }

    public void setSelected(FuncionarioEntity selected) {
        this.selected = selected;
    }
    
    private List<FuncionarioEntity> funcionarioList = new ArrayList<>();
    
    public FuncionarioEntity prepareAdicionar() {
        funcionario = new FuncionarioEntity();
        return funcionario;
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
                        ejbFacade.createReturn(funcionario);
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
    
    public void adicionarFuncionario() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        funcionario.setDatahorareg(datahoraAtual);
        persist(FuncionarioController.PersistAction.CREATE,
                "Registro incluído com sucesso!"
        );
    }
    
    public void editarFuncionario() {
        persist(FuncionarioController.PersistAction.UPDATE,
                "Registro alterado com sucesso!"
        );
    }
    
    public void deletarFuncionario() {
        persist(FuncionarioController.PersistAction.DELETE,
                "Registro excluído com sucesso!"
        );
    }
    
}
