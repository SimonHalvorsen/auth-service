package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class AbstractDao<T> {

    private Class<T> entityClass;

    @PersistenceContext(name = "PersistenceUnit")
    protected EntityManager em;

    public AbstractDao(Class<T> entityClass){

        this.entityClass = entityClass;
    }

    public void edit(T entity){

        em.merge(entity);
    }

    public void persist(T entity){

        em.persist(entity);
    }

    public void remove(T entity){

        em.remove(em.merge(entity));
    }

    public T find(Object id){

        return em.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(){
        CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
    }
}