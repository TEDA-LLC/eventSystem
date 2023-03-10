package com.example.eventsystem.events;

import com.example.eventsystem.model.Employee;
import com.example.eventsystem.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HibernateEventListener implements PostUpdateEventListener {
    private final ChangeService changeService;

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        try {
            Object entity = postUpdateEvent.getEntity();
            EntityPersister persister = postUpdateEvent.getPersister();
            String entityName = persister.getClassMetadata().getEntityName();
            String[] propertyNames = persister.getClassMetadata().getPropertyNames();
            Object[] oldState = postUpdateEvent.getOldState();
            Object[] newState = postUpdateEvent.getState();
            Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int[] changedIndexes = postUpdateEvent.getDirtyProperties();
            for (int i = 0; i < changedIndexes.length; i++) {
                String columnName = propertyNames[postUpdateEvent.getDirtyProperties()[i]];
                String oldData = String.valueOf(oldState[changedIndexes[i]]);
                String newData = String.valueOf(newState[changedIndexes[i]]);
                changeService.changeSaver(employee, entityName, columnName, oldData, newData);
            }
        }catch (Exception ignored){
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}