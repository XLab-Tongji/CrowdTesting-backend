package com.example.now.controller;
import com.example.now.service.TaskDataService;
import com.example.now.entity.TaskData;
import com.example.now.entity.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/taskData")
public class TaskDataController {
    @Autowired
    private TaskDataService taskDataService;
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResultMap taskDataFind(int id){
        List<TaskData> taskData=taskDataService.findTaskData(id);
        if(taskData.isEmpty()){
            return new ResultMap().success("204").message("this task has no data for now");
        }
        return new ResultMap().success().data("taskData",taskData);
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultMap taskDataAdd(int id,int worker_num,float progress_rate){
        String message=taskDataService.addTaskData(id,worker_num,progress_rate);
        if(message!="succeed"){
            return new ResultMap().fail("400").message(message);
        }
        return new ResultMap().success("201").message(message);
    }
}
