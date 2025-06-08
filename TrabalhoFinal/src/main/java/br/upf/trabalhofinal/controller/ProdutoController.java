package br.upf.trabalhofinal.controller;

import br.upf.trabalhofinal.entity.ProdutoEntity;
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

@Named(value = "produtoController")
@SessionScoped
public class ProdutoController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinal.facade.ProdutoFacade ejbFacade;

    public ProdutoController() {
    }
    
    private ProdutoEntity produto = new ProdutoEntity();   
    
    private ProdutoEntity selected;
    
    private List<ProdutoEntity> produtoList = new ArrayList<>();

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public ProdutoEntity getSelected() {
        return selected;
    }

    public void setSelected(ProdutoEntity selected) {
        this.selected = selected;
    }

    public List<ProdutoEntity> getProdutoList() {
        return ejbFacade.buscarTodos();
    }

    public void setProdutoList(List<ProdutoEntity> produtoList) {
        this.produtoList = produtoList;
    }
    
    public ProdutoEntity prepareAdicionar() {
        produto = new ProdutoEntity();
        return produto;
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
                        ejbFacade.createReturn(produto);
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
    
    public void adicionarProduto() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        produto.setDatahorareg(datahoraAtual);
        persist(ProdutoController.PersistAction.CREATE,
                "Registro incluído com sucesso!"
        );
        
        produto = new ProdutoEntity();
    }
    
    public void editarProduto() {
        persist(ProdutoController.PersistAction.UPDATE,
                "Registro alterado com sucesso!"
        );
    }
    
    public void deletarProduto() {
        persist(ProdutoController.PersistAction.DELETE,
                "Registro excluído com sucesso!"
        );
    }
    
}
