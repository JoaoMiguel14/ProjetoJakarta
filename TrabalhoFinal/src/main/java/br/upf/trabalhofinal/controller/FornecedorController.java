package br.upf.trabalhofinal.controller;

import br.upf.trabalhofinal.entity.FornecedorEntity;
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

@Named(value = "fornecedorController")
@SessionScoped
public class FornecedorController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinal.facade.FornecedorFacade ejbFacade;

    public FornecedorController() {
    }
    
    private FornecedorEntity fornecedor = new FornecedorEntity();   
    
    private FornecedorEntity selected;
    
    private List<FornecedorEntity> fornecedorList = new ArrayList<>();

    public FornecedorEntity getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorEntity fornecedor) {
        this.fornecedor = fornecedor;
    }

    public FornecedorEntity getSelected() {
        return selected;
    }

    public void setSelected(FornecedorEntity selected) {
        this.selected = selected;
    }

    public List<FornecedorEntity> getFornecedorList() {
        return ejbFacade.buscarTodos();
    }

    public void setFornecedorList(List<FornecedorEntity> fornecedorList) {
        this.fornecedorList = fornecedorList;
    }
    
    public FornecedorEntity prepareAdicionar() {
        fornecedor = new FornecedorEntity();
        return fornecedor;
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
                        ejbFacade.createReturn(fornecedor);
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
    
    public void adicionarFornecedor() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        fornecedor.setDatahorareg(datahoraAtual);
        persist(FornecedorController.PersistAction.CREATE,
                "Registro incluído com sucesso!"
        );
    }
    
    public void editarFornecedor() {
        persist(FornecedorController.PersistAction.UPDATE,
                "Registro alterado com sucesso!"
        );
    }
    
    public void deletarFornecedor() {
        persist(FornecedorController.PersistAction.DELETE,
                "Registro excluído com sucesso!"
        );
    }
    
}
