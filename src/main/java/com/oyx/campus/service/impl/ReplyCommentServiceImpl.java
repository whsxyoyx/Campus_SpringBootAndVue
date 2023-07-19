package com.oyx.campus.service.impl;

import com.oyx.campus.bean.ReplyComment;
import com.oyx.campus.mapper.ReplyCommentMapper;
import com.oyx.campus.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author oyx
 * @create 2023-04-06 20:38
 */
@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {
    @Autowired
    private ReplyCommentMapper replyCommentMapper;

    @Override
    public int saveReply(ReplyComment comment) {
        return replyCommentMapper.insertSelective(comment);
    }

    @Override
    public List<ReplyComment> getAllReplyByTid(long tid,long cid) {
        return replyCommentMapper.getAllReplyByTid(tid,cid);
    }
}
