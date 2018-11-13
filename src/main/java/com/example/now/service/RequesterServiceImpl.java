package com.example.now.service;
import com.example.now.entity.Requester;
import com.example.now.service.RequesterService;
import com.example.now.util.TokenUtils;
import com.example.now.repository.RequesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RequesterServiceImpl implements RequesterService{
    @Autowired
    private RequesterRepository requesterRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Override
    public Requester findRequesterById(int id){
        return requesterRepository.findById(id);
    }
    @Override
    public Requester findRequesterByUsername(String username){
        return requesterRepository.findByUsername(username);
    }
    @Override
    public String addRequester(String username,String name){
        if(username==""||name=="")
        {
            return "username or name is empty";
        }
        Requester worker = new Requester(username,name);
        requesterRepository.saveAndFlush(worker);
        return "succeed";
    }
    @Override
    public String updateRequester(Requester requester){
        requesterRepository.saveAndFlush(requester);
        return "succeed";
    }
    @Override
    public String deleteRequester(int id){
        requesterRepository.deleteById(id);
        requesterRepository.flush();
        return "succeed";
    }
}
