package com.example.eventsystem.service;

import com.example.eventsystem.model.Change;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.repository.ChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeService {
    private final ChangeRepository changeRepository;

    public void changeSaver(Long columnId,Employee employee, String table, String column, String oldData, String newData) {
        Change change = new Change();
        change.setEmployee(employee);
        change.setOldData(oldData);
        change.setNewData(newData);
        change.setColumnName(column);
        change.setTableName(table);
        change.setColumnId(columnId);
        changeRepository.save(change);
    }
}
