package com.example.now.controller;

import com.example.now.entity.IdStore;
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

    //查找所有worker，具有管理员权限可以调用
    @RequestMapping(value = "/find-all",method = RequestMethod.GET)
    public ResultMap workerFindAll(){
        return new ResultMap().success().data("workers",workerService.findAllWorker());
    }

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
    public ResultMap workerAdd(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, String school) {                      //创建一个worker
        IdStore idStore=new IdStore();
        String message = workerService.addWorker(username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,idStore,school);
        return new ResultMap().success("201").message(message).data("workerId",idStore.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResultMap workerUpdate(String username, String name, String teleNumber, String eMail, String withdrawnMethod, String education, String workArea, int age, String gender, String major, String school) {                      //修改worker
        String authToken = request.getHeader(this.tokenHeader);
        String temp = this.tokenUtils.getUsernameFromToken(authToken);
        Worker the_worker = workerService.findWorkerByUsername(temp);
        String message = workerService.updateWorker(the_worker.getId(),username,name,teleNumber,eMail,withdrawnMethod,education,workArea,age,gender,major,school,the_worker.getCorrect_number_answered(),the_worker.getAll_number_answered(), the_worker.getOvertime_number(),the_worker.getBalance());
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
