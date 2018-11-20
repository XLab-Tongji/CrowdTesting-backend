package com.example.now.controller;

import com.example.now.entity.Worker;
import com.example.now.service.WorkerService;
import com.example.now.entity.ResultMap;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public ResultMap workerFindById(int id) {
        return new ResultMap().success().data("worker", workerService.findWorkerById(id));
    }           //根据ID查找worker

    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET)
    public ResultMap workerFindByUsername(String username) {                           //根据名字查找worker
        Worker worker = workerService.findWorkerByUsername(username);
        if (worker == null) {
            return new ResultMap().fail("204").message("can not find worker");
        }
        return new ResultMap().success().data("worker", worker);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMap workerAdd(String username, String name) {                      //创建一个worker
        String message = workerService.addWorker(username, name);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap workerUpdate(Worker worker) {                      //修改worker
        String message = workerService.updateWorker(worker);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultMap workerDelete(int id) {                      //删除worker
        String message = workerService.deleteWorker(id);
        return new ResultMap().success("201").message(message);
    }

    @RequestMapping(value = "/find-myself", method = RequestMethod.GET)
    public ResultMap findMyself() {
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        return new ResultMap().success().data("worker", workerService.findWorkerByUsername(username));
    }
}
