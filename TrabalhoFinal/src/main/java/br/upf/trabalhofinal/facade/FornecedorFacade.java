package br.upf.trabalhofinal.facade;

import br.upf.trabalhofinal.entity.FornecedorEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FornecedorFacade extends AbstractFacade<FornecedorEntity> {
    
    @PersistenceContext(unitName = "TrabalhoFinalPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public FornecedorFacade() {
        super(FornecedorEntity.class);
    }
    
    private List<FornecedorEntity> entityList;
    
    public List<FornecedorEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT fo FROM FornecedorEntity fo order by fo.id asc");
            
            if(!query.getResultList().isEmpty()) {
                entityList = (List<FornecedorEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
}
