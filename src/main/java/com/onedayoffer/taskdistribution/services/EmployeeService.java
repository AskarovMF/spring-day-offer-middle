package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.repositories.EmployeeRepository;
import com.onedayoffer.taskdistribution.repositories.TaskRepository;
import com.onedayoffer.taskdistribution.repositories.entities.Employee;
import com.onedayoffer.taskdistribution.repositories.entities.Task;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private static final String SORT_ASC = "ASC";
    private static final String SORT_DESC = "DESC";

    public List<EmployeeDTO> getEmployees(@Nullable String sortDirection) {
        List<Employee> employeeList;

        if (SORT_ASC.equalsIgnoreCase(sortDirection)) {
            employeeList = employeeRepository.findAllAndSort(Sort.by(Sort.Direction.ASC, "fio"));
        } else if (SORT_DESC.equalsIgnoreCase(sortDirection)){
            employeeList = employeeRepository.findAllAndSort(Sort.by(Sort.Direction.DESC, "fio"));
        } else {
            employeeList = employeeRepository.findAll();
        }

         Type listType = new TypeToken<List<EmployeeDTO>>() {}.getType();

         return modelMapper.map(employeeList, listType);
    }

    @Transactional
    public EmployeeDTO getOneEmployee(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseGet(Employee::new);

        Type employeeType = new TypeToken<EmployeeDTO>() {}.getType();

        return modelMapper.map(employee, employeeType);
    }

    public List<TaskDTO> getTasksByEmployeeId(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseGet(Employee::new);
        Type employeeType = new TypeToken<List<TaskDTO>>() {}.getType();

        return modelMapper.map(employee.getTasks(), employeeType);
    }

    @Transactional
    public void changeTaskStatus(Integer taskId, TaskStatus status) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(status);
            taskRepository.save(task);
        }
    }

    @Transactional
    public void postNewTask(Integer employeeId, TaskDTO newTask) {
        throw new java.lang.UnsupportedOperationException("implement postNewTask");
    }
}
