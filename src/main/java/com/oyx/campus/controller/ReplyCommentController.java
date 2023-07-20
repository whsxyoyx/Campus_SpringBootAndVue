package com.oyx.campus.controller;

import com.oyx.campus.bean.Msg;
import com.oyx.campus.bean.ReplyComment;
import com.oyx.campus.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author oyx
 * @create 2023-04-06 20:40
 */


@RestController
@RequestMapping(value = "/replyComment")
public class ReplyCommentController {
    @Autowired
    private ReplyCommentService replyCommentService;
    //用户回复评论
    @PostMapping("/saveReply")
    public Msg saveReply(@RequestBody ReplyComment comment){
        System.out.println("comment--->"+comment);
        comment.setCreatetime(new Date());
        int re = replyCommentService.saveReply(comment);
        if(re>0){
            return Msg.success("回复成功");
        }else{
            return Msg.fail("回复失败");
        }
    }
}
