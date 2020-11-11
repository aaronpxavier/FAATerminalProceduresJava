package com.fltprep.charts;

import javax.persistence.*;

public class ChartService {
    @PersistenceUnit(name = "ChartsUnit")
    private EntityManagerFactory entityManagerFactory;

    public ChartService() {}

    public void addCharts(ChartMap chartMap) {
        entityManagerFactory = Persistence.createEntityManagerFactory("ChartsUnit");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        for(Chart chart: chartMap) {
            try {
                et = em.getTransaction();
                et.begin();
                em.persist(chart);
                et.commit();
            } catch(Exception e) {
                if(et!=null) {
                    et.rollback();
                }
                e.printStackTrace();
            } finally {
                if(entityManagerFactory.isOpen()) {
                    entityManagerFactory.close();
                }
            }
        }
        em.close();
    }
}
