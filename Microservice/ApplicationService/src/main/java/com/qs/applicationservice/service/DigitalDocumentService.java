package com.qs.applicationservice.service;

import com.qs.applicationservice.dao.DigitalDocumentDAO;
import com.qs.applicationservice.entity.DigitalDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DigitalDocumentService {
    private DigitalDocumentDAO dao;
    @Autowired
    public DigitalDocumentService(DigitalDocumentDAO dao) {
        this.dao = dao;
    }
    @Transactional
    public int create(DigitalDocument digitalDocument){
        return dao.create(digitalDocument);
    }
    @Transactional
    public DigitalDocument get(int id){return dao.get(id);}
    @Transactional
    public void delete(int id){
        dao.delete(id);
    }
    @Transactional
    public void deleteByPath(String path){
        dao.deleteByPath(path);
    }
    @Transactional
    public DigitalDocument getByPath(String path){
        return dao.getByPath(path);
    }
    @Transactional
    public void approveDoc(String path){
        dao.updateStatus(path,"APPROVED");
    }
    @Transactional
    public void rejectDoc(String path,String reason){
        String description = "REJECTED Reason: " + reason;
        dao.updateStatus(path,description);
    }
    @Transactional
    public String getDocStatus(String path) {
        String status = dao.getDocStatus(path);
        if (status.contains("REJECTED")){
            return status;
        }
        else if (status.contains("APPROVED")){
            return status;
        }
        else if(status.equals("EMPTY")){
            return "EMPTY";
        }
        else{
            return "PENDING";
        }
    }
}
