package com.oyx.campus.service;

import com.oyx.campus.bean.ReplyComment;

import java.util.List;

/**
 * @author oyx
 * @create 2023-04-06 20:37
 */
public interface ReplyCommentService {
    int saveReply(ReplyComment comment);
    List<ReplyComment> getAllReplyByTid(long tid,long cid);
}
