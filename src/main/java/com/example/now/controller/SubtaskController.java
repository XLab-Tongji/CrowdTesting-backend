package com.example.now.controller;

import com.example.now.entity.IdStore;
import com.example.now.entity.ResultMap;
import com.example.now.service.SubtaskService;
import com.example.now.service.TaskService;
import com.example.now.service.WorkerService;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/subtask")
public class SubtaskController {
    @Value("X_Auth_Token")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private SubtaskService subtaskService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/find-all",method = RequestMethod.GET)
    public ResultMap subtaskFindAll(){
        return new ResultMap().success().data("subtasks",subtaskService.findAllSubtask());
    }

    // worker 领取任务时调用
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultMap subtaskAdd(Integer taskId,Integer beginAt,Integer endAt,Integer typeOfSubtask){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        IdStore subtaskId=new IdStore();
        String message=subtaskService.addSubtask(taskId,workerService.findWorkerByUsername(username).getWorkerId(),beginAt,endAt,typeOfSubtask,subtaskId);
        //更新 distributedNumber 字段
        //并检测该 task 是否分配完成,若完成,修改 isDistributed 字段
        String msg=taskService.updateDistributedNumber(taskId,beginAt,endAt);
        if(!message.equals("succeed")){
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message).data("subtaskId",subtaskId.getId());
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResultMap subtaskDelete(int id){
        String message=subtaskService.deleteSubtask(id);
        return new ResultMap().success("201").message(message);
    }
}
