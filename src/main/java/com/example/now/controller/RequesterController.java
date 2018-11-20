package com.example.now.controller;

import com.example.now.entity.Requester;
import com.example.now.service.RequesterService;
import com.example.now.entity.ResultMap;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requester")
public class RequesterController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap requesterFindById(int id) {
        return new ResultMap().success().data("requester", requesterService.findRequesterById(id));
    }           //根据ID查找requester

    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET)
    public ResultMap requesterFindByUsername(String username) {                           //根据名字查找requester
        Requester requester = requesterService.findRequesterByUsername(username);
        if (requester == null) {
            return new ResultMap().fail("204").message("can not find requester");
        }
        return new ResultMap().success().data("requester", requester);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap requesterAdd(String username, String name) {                      //创建一个requester
        String message = requesterService.addRequester(username, name);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap requesterUpdate(Requester requester) {                      //修改requester
        String message = requesterService.updateRequester(requester);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap requesterDelete(int id) {                      //删除requester
        String message = requesterService.deleteRequester(id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-myself", method = RequestMethod.GET)
    public ResultMap findMyself() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return new ResultMap().success().data("requester", requesterService.findRequesterByUsername(username));
    }
}
