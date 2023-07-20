package com.oyx.campus.controller;

import com.oyx.campus.bean.Msg;
import com.oyx.campus.bean.ReplyComment;
import com.oyx.campus.bean.TaskComment;
import com.oyx.campus.service.ReplyCommentService;
import com.oyx.campus.service.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


//@CrossOrigin
@RestController
@RequestMapping(value = "/comment")
public class TaskCommentController {
    @Autowired
    private TaskCommentService taskCommentService;
    @Autowired
    private ReplyCommentService replyCommentService;

    //用户回复评论
    @PostMapping("/saveReply")
    public Msg saveReply(@RequestBody ReplyComment comment){
        comment.setCreatetime(new Date());
        int re = replyCommentService.saveReply(comment);
        if(re>0){
            return Msg.success("回复成功");
        }else{
            return Msg.fail("回复失败");
        }
    }

    //用户提交评论
    @PostMapping("/save")
    public Msg save(@RequestBody TaskComment comment){
        comment.setCreatetime(new Date());
        int re = taskCommentService.save(comment);
        if(re>0){
            return Msg.success("评论成功");
        }else{
            return Msg.fail("评论失败");
        }
    }

    //查询指定任务id的所有评论
    /* 二级评论功能封装方式二，*/
    @GetMapping("/getComment/{tid}")
    public Msg getCommentById(@PathVariable("tid") int tid){
        List<TaskComment> list = taskCommentService.getCommentByIdTest(tid);
        list.forEach(System.out::println);
        return Msg.success().add("comments",list);
    }


    //查询指定任务id的所有评论
    /*
    二级评论功能封装方式一，
    先查task_id为指定id的所有评论，
    再每次将task_id和查出来的common_id放入查询出满足条件的回复数据
    最后封装进当前遍历的TaskComment
    * */
/*
    @GetMapping("/getComment/{tid}")
    public Msg getCommentById(@PathVariable("tid") int tid){
        List<TaskComment> list = taskCommentService.getCommentById(tid);
        for(TaskComment task:list){
            List<ReplyComment> allReplyByTid = replyCommentService.getAllReplyByTid(tid, task.getCommonId());
            System.out.println("--<"+task.getCommonId()+">--start");
//            allReplyByTid.forEach(System.out::println);
            System.out.println("--<"+task.getCommonId()+">--end");

            task.setChildren(allReplyByTid);
        }
        return Msg.success().add("comments",list);
    }
*/





}
