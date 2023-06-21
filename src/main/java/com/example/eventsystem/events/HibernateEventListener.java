//package com.example.eventsystem.events;
//
//import com.example.eventsystem.model.Employee;
//import com.example.eventsystem.service.ChangeService;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.event.spi.PostUpdateEvent;
//import org.hibernate.event.spi.PostUpdateEventListener;
//import org.hibernate.persister.entity.EntityPersister;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class HibernateEventListener implements PostUpdateEventListener {
//    private final ChangeService changeService;
//
//    @Override
//    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
//        try {
//            Object entity = postUpdateEvent.getEntity();
//            EntityPersister persister = postUpdateEvent.getPersister();
//            String entityName = persister.getClassMetadata().getEntityName();
//            String[] propertyNames = persister.getClassMetadata().getPropertyNames();
//            Object[] oldState = postUpdateEvent.getOldState();
//            Object[] newState = postUpdateEvent.getState();
//            Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            int[] changedIndexes = postUpdateEvent.getDirtyProperties();
//            Long id = 0L;
//            String string = entity.toString();
//            int index1 = string.indexOf("(");
//            int index2 = string.indexOf(",");
//            id = Long.valueOf(string.substring(index1+4, index2));
//            for (int i = 0; i < changedIndexes.length; i++) {
//                String columnName = propertyNames[postUpdateEvent.getDirtyProperties()[i]];
//                String oldData = String.valueOf(oldState[changedIndexes[i]]);
//                String newData = String.valueOf(newState[changedIndexes[i]]);
//                changeService.changeSaver(id, employee, entityName, columnName, oldData, newData);
//            }
//        } catch (Exception ignored) {
//            System.err.println(ignored.getMessage());
//        }
//    }
//
//    @Override
//    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
//        return false;
//    }
//}