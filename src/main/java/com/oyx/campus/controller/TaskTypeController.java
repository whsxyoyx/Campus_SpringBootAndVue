package com.oyx.campus.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyx.campus.bean.Msg;
import com.oyx.campus.bean.TaskType;
import com.oyx.campus.service.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/taskType")
public class TaskTypeController {
    @Autowired
    private TaskTypeService taskTypeService;
    //查询所有任务类型
    @RequestMapping("/getAllTaskType")
    public Msg getAllTaskType(){
        List<TaskType> allTaskType = taskTypeService.getAllTaskType();
//        allTaskType.forEach(System.outprintln);

        return Msg.success().add("taskTypes",allTaskType);
    }

    //查询所有任务类型之分页
    @RequestMapping("/getAllTaskType/{pn}/{pageSize}")
    public Msg getAllTaskTypePage(@PathVariable("pn")Integer pn,
                                  @PathVariable("pageSize")Integer pageSize){
        PageHelper.startPage(pn,pageSize);
        List<TaskType> list = taskTypeService.getAllTaskType();
        PageInfo<TaskType> page = new PageInfo<TaskType>(list);
        return Msg.success().add("taskTypes",page);
    }


    //添加任务类型
    @PostMapping("/addTaskType")
    public Msg addTaskType(@RequestBody Map<String,Object> tasktype){
        String name=(String)tasktype.get("tasktype");
        System.out.println(tasktype);
        if (name == null) {
            return Msg.fail("添加失败");
        } else {
            if (name.length() == 0) {
                return Msg.fail("添加失败");
            }
        }
        TaskType taskType = new TaskType(new Date(), name);
        int i=taskTypeService.addTaskType(taskType);
        if (i > 0) {
            return Msg.success("添加成功");
        } else {
            return Msg.fail("添加失败");
        }


    }

    //删除任务类型
    @DeleteMapping("/delTaskType/{tasktype}")
    public Msg delTaskType(@PathVariable("tasktype")String tasktype){
        int r = taskTypeService.delTaskType(tasktype);
        if (r > 0) {
            return Msg.success("删除成功");
        } else {
            return Msg.fail("删除失败");
        }
    }

    //修改任务类型
    @PutMapping("/updateTaskType")
    public Msg updateTaskType(@RequestBody Map<String,Object> tasktype){


        String name=(String)tasktype.get("name");
        String oldName=(String)tasktype.get("oldName");
        System.out.println(tasktype);
        if (name == null) {
            return Msg.fail("任务类型不能为空啊");
        } else {
            if (name.length() == 0) {
                return Msg.fail("任务类型长度不能为0啊");
            }
        }
        TaskType taskType = new TaskType(null, name);
        int r = taskTypeService.updateTaskType(taskType,oldName);
        if (r > 0) {
            return Msg.success("修改成功");
        } else {
            return Msg.fail("修改失败");
        }
    }

}
