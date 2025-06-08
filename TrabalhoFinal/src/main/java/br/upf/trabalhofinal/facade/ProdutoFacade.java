package br.upf.trabalhofinal.facade;

import br.upf.trabalhofinal.entity.ProdutoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProdutoFacade extends AbstractFacade<ProdutoEntity> {
    
    @PersistenceContext(unitName = "TrabalhoFinalPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ProdutoFacade() {
        super(ProdutoEntity.class);
    }
    
    private List<ProdutoEntity> entityList;
    
    public List<ProdutoEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM ProdutoEntity p order by p.id asc");
            
            if(!query.getResultList().isEmpty()) {
                entityList = (List<ProdutoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
}
