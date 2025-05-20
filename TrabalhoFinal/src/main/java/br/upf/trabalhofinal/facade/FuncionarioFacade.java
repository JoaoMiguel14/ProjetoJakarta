package br.upf.trabalhofinal.facade;

import br.upf.trabalhofinal.entity.FuncionarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FuncionarioFacade extends AbstractFacade<FuncionarioEntity> {
    
    @PersistenceContext(unitName = "TrabalhoFinalPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public FuncionarioFacade() {
        super(FuncionarioEntity.class);
    }
    
    private List<FuncionarioEntity> entityList;
    
    public List<FuncionarioEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT f FROM FuncionarioEntity f order by f.id asc");
            
            if(!query.getResultList().isEmpty()) {
                entityList = (List<FuncionarioEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
    
}
