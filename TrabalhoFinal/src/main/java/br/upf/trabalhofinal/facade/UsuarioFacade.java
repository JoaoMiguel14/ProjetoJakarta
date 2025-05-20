package br.upf.trabalhofinal.facade;

import br.upf.trabalhofinal.entity.UsuarioEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsuarioFacade extends AbstractFacade<UsuarioEntity> {
    
    @PersistenceContext(unitName = "TrabalhoFinalPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsuarioFacade() {
        super(UsuarioEntity.class);
    }
    
    private List<UsuarioEntity> entityList;
    
    public List<UsuarioEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT u FROM UsuarioEntity u order by u.nome");
            
            if(!query.getResultList().isEmpty()) {
                entityList = (List<UsuarioEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
    
    public UsuarioEntity buscarPorEmail(String email, String senha) {
        UsuarioEntity usuario = new UsuarioEntity();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT u FROM UsuarioEntity u WHERE u.email = :email AND u.senha = :senha");
            query.setParameter("email", email);
            query.setParameter("senha", senha);
            
            if(!query.getResultList().isEmpty()) {
                usuario = (UsuarioEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return usuario;
    }
    
}
