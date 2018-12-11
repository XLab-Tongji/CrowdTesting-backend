package com.example.now.service.Iml;

import com.example.now.entity.IdStore;
import com.example.now.entity.Requester;
import com.example.now.service.RequesterService;
import com.example.now.util.TokenUtils;
import com.example.now.repository.RequesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequesterServiceImpl implements RequesterService {
    @Autowired
    private RequesterRepository requesterRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Requester findRequesterById(int id) {
        return requesterRepository.findById(id);
    }

    @Override
    public Requester findRequesterByUsername(String username) {
        return requesterRepository.findByEMail(username);
    }

    @Override
    public String addRequester(String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age, IdStore id) {
        if (username == "" || name == "") {
            return "username or name is empty";
        }
        Requester requester = new Requester(username, name,teleNumber,eMail,research_field,institutionName,address,payMethod,gender,age);
        Requester temp=requesterRepository.saveAndFlush(requester);
        id.setId(temp.getRequesterId());
        return "succeed";
    }

    @Override
    public String updateRequester(int requesterId,String username, String name, String teleNumber, String eMail, String research_field, String institutionName, String address, String payMethod, String gender, int age) {
        Requester requester=requesterRepository.findById(requesterId);
        requester.setAll(username, name,teleNumber,eMail,research_field,institutionName,address,payMethod,gender,age);
        requesterRepository.saveAndFlush(requester);
        return "succeed";
    }

    @Override
    public String deleteRequester(int id) {
        requesterRepository.deleteById(id);
        requesterRepository.flush();
        return "succeed";
    }
}
