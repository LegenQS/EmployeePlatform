package com.qs.userservice.repository;

import com.qs.userservice.domain.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmplpyeeRepo extends MongoRepository<Employee,String> {

    List<Employee> findEmployeeByUserID(String id);

    List<Employee> findEmployeeByHouseId(String id);

    List<Employee> findEmployeeById(String id);
}
