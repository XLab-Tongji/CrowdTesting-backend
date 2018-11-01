package com.example.now.controller;
import com.example.now.entity.Requester;
import com.example.now.repository.RequesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequesterController {
    @Autowired
    private RequesterRepository requesterRepository;
    @RequestMapping(value = "/requesterid",method = RequestMethod.GET)
    public Requester getRequesterById(int id){
        return requesterRepository.findById(id);
    }           //根据ID查找requester
    @RequestMapping(value = "/requesterusername",method = RequestMethod.GET)
    public Requester getRequesterByUsername(String username){                           //根据名字查找requester
        return requesterRepository.findByUsername(username);
    }

    @RequestMapping(value = "/requester",method = RequestMethod.PUT)
    public Requester createRequester(String username,String name){                      //创建一个requester
        Requester requester = new Requester(username,name);
        return requesterRepository.save(requester);
    }

    @RequestMapping(value = "/requester",method = RequestMethod.POST)
    public Requester updateRequester(Requester requester){                      //修改requester
        return requesterRepository.save(requester);
    }

    @RequestMapping(value = "/requester",method = RequestMethod.DELETE)
    public void deleteRequester(int id){                      //删除requester
        requesterRepository.deleteById(id);
    }
}
