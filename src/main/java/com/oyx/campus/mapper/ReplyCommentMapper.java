package com.oyx.campus.mapper;

import com.oyx.campus.bean.ReplyComment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-19 10:01
 */
public interface ReplyCommentMapper {
    /*
    *
    * */
    @Select("""
            select * from `reply_comment`
            where task_id = #{tid}
            AND reply_id = #{cid}
            """)
    List<ReplyComment>  getAllReplyByTid(long tid,long cid);
    /*
    * 添加回复
    * */
    int insertSelective(ReplyComment replyComment);
}
