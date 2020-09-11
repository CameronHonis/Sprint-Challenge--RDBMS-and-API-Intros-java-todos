package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "todosService")
public class TodosServiceImpl implements TodosService{
    @Autowired
    private TodosRepository todosrepos;

    @Override
    public Todos findTodoById(long id){
        return todosrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void markComplete(long id){
        Todos todo = findTodoById(id);
        todo.setCompleted(true);
        todosrepos.save(todo);
    }

    @Transactional
    @Override
    public Todos update(long id, Todos updateTodos){
        Todos todo = findTodoById(id);
        if (updateTodos.getDescription() != null){
            todo.setDescription(updateTodos.getDescription());
        }
        if (updateTodos.getUser() != null) {
            todo.setUser(updateTodos.getUser());
        }
        if (updateTodos.hasvalueforcompleted){
            todo.setCompleted(updateTodos.isCompleted());
        }
        return todosrepos.save(todo);
    }
}
