package com.qs.applicationservice.service;

import com.qs.applicationservice.dao.ApplicationWorkflowDAO;
import com.qs.applicationservice.entity.ApplicationWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ApplicationService {
    private ApplicationWorkflowDAO dao;
    @Autowired
    public ApplicationService(ApplicationWorkflowDAO dao) {
        this.dao = dao;
    }
    @Transactional
    public int create(ApplicationWorkflow applicationWorkflow){
        return dao.create(applicationWorkflow);
    }
    @Transactional
    public ApplicationWorkflow getByEmployeeId(String employee_id){return dao.getByEmployeeId(employee_id);}
    @Transactional
    public ApplicationWorkflow getByApplicationWorkflowId(int id){return dao.getByApplicationWorkflowId(id);}
    @Transactional
    public void reject(String employee_id, String comment){
        dao.updateStatus(employee_id,ApplicationWorkflow.STATUS_REJECTED, comment);
    }
    @Transactional
    public void approve(String employee_id, String comment){
        dao.updateStatus(employee_id,ApplicationWorkflow.STATUS_APPROVED, comment);
    }
    @Transactional
    public void delete(int id){
        dao.delete(id);
    }
    @Transactional
    public List<ApplicationWorkflow> getAll(){
        return dao.getAll();
    }
    @Transactional
    public List<ApplicationWorkflow> getAllPending(){
        return dao.getAllPending();
    }
    @Transactional
    public List<ApplicationWorkflow> getAllApproved(){
        return dao.getAllApproved();
    }
    @Transactional
    public List<ApplicationWorkflow> getAllRejected(){
        return dao.getAllRejected();
    }
    @Transactional
    public String getApplicationStatus(String employee_id){
        return dao.getApplicationStatus(employee_id);
    }
}
