package com.example.now.util;

import com.example.now.entity.Task;

import java.util.Iterator;
import java.util.List;

public class TaskUtil {

    //挑选出已被审核的任务
    public static List<Task> selectReviewedTask(List<Task> tasks){
        for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
            Task task = it.next();
            if(task.getReviewed()==0) {
                it.remove();
            }
        }
        return tasks;
    }
}

