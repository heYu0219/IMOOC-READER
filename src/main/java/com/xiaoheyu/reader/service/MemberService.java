package com.xiaoheyu.reader.service;

import com.xiaoheyu.reader.entity.Member;
import com.xiaoheyu.reader.entity.MemberReadState;

public interface MemberService {
    /**
     * 会员注册，创建新会员
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 会员对象
     */
    public Member createMember(String username,String password,String nickname);

    public Member checkLogin(String username,String password);

    /**
     * 根据图书编号和会员编号查询用户对于当前这本书是否有阅读状态
     * @param memberId 会员编号
     * @param bookId 图书编号
     * @return 阅读状态对象
     */
    public MemberReadState selectMemberReadState(Long memberId,Long bookId);

    /**
     * 更新阅读状态
     * @param memberId 会员编号
     * @param bookId 图书id
     * @param readState 阅读状态
     * @return 阅读状态对象
     */
    public MemberReadState updateMemberReadState(Long memberId,Long bookId,Integer readState);
}
