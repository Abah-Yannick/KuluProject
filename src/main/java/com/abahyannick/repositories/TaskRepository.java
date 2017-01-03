package com.abahyannick.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import com.abahyannick.models.Task;

@RepositoryRestResource
@Component
public interface TaskRepository extends JpaRepository<Task, Integer> {


}
