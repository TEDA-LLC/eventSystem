//package com.example.eventsystem.events;
//
//
//import lombok.RequiredArgsConstructor;
//import org.hibernate.event.service.spi.EventListenerRegistry;
//import org.hibernate.event.spi.EventType;
//import org.hibernate.internal.SessionFactoryImpl;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManagerFactory;
//
//@Component
//@RequiredArgsConstructor
//public class HibernateEventRegister {
//
//    private final EntityManagerFactory entityManagerFactory;
//    private final HibernateEventListener hibernateInsertListener;
//
//    @PostConstruct
//    public void registerListeners() {
//        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
//        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//        registry.getEventListenerGroup(EventType.POST_UPDATE).prependListener(hibernateInsertListener);
//    }
//}