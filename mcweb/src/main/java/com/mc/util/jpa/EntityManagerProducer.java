package com.mc.util.jpa;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

@ApplicationScoped
public class EntityManagerProducer implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(EntityManagerProducer.class);

    private EntityManagerFactory factory;

    @PostConstruct // ✅ CDI gerencia o ciclo de vida corretamente
    public void init() {
        log.info("Criando EntityManagerFactory: mcPU");
        this.factory = Persistence.createEntityManagerFactory("mcPU");
    }

    @Produces
    @RequestScoped
    public EntityManager create() {
        log.info("Criou o EntityManager");
        return factory.createEntityManager();
    }

    public void close(@Disposes EntityManager manager) {
        if (manager.isOpen()) {
            manager.close();
            log.info("Fechou o EntityManager");
        }
    }

    @PreDestroy // ✅ fecha o factory quando a aplicação encerrar
    public void destroy() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            log.info("Fechou o EntityManagerFactory");
        }
    }
}