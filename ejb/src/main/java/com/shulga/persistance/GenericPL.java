package com.shulga.persistance;

import com.shulga.model.HasId;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public class GenericPL<T extends Serializable & HasId> {

    @PersistenceContext(unitName = "primary")
    protected EntityManager em;
    private Class<T> type;

    public GenericPL(Class<T> type) {
        this.type = type;
    }

    public GenericPL() {
    }

    public Long create(T obj) {
        em.persist(obj);
        return obj.getId();
    }

    public void delete(Long id) {
        em.remove(em.getReference(type, id));
        em.flush();
    }

    public void update(Object obj) {
        em.merge(obj);
    }

    public T get(Long id) {
        return em.find(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getListByQBE(T qbe) {
        return getCriteria(qbe).list();
    }

    public void flushAndClear() {
        em.flush();
        em.clear();
    }

    public Criteria getCriteria(T qbe, String... excludeProperties) {
        Example example = Example.create(qbe).excludeZeroes();
        for (String prop : excludeProperties) {
            example.excludeProperty(prop);
        }
        Session session = (Session) em.getDelegate();
        return session.createCriteria(qbe.getClass()).add(example).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }


}
