package com.xiaoheyu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoheyu.reader.entity.Member;
import com.xiaoheyu.reader.entity.MemberReadState;
import com.xiaoheyu.reader.mapper.MemberMapper;
import com.xiaoheyu.reader.mapper.MemberReadStateMapper;
import com.xiaoheyu.reader.service.MemberService;
import com.xiaoheyu.reader.service.exception.BussinessException;
import com.xiaoheyu.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);//查询是否已有相同用户名的会员
        List<Member> memberList=memberMapper.selectList(queryWrapper);
        //判断用户名是否已经存在
        if(memberList.size()>0){
            throw new BussinessException("M01","用户名已存在");
        }
        Member member=new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt=new Random().nextInt(1000)+1000;//盐值
        String pwd=MD5Utils.MD5Digest(password,salt);//计算MD5摘要
        member.setSalt(salt);
        member.setPassword(pwd);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Member member = memberMapper.selectOne(queryWrapper);//根据用户名查询会员信息
        if (member==null){
            throw new BussinessException("M02","用户不存在");
        }
        String md5=MD5Utils.MD5Digest(password,member.getSalt());
        if (!md5.equals(member.getPassword())){
            throw new BussinessException("M03","密码错误");
        }
        return member;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("book_id",bookId);
        MemberReadState memberReadState=memberReadStateMapper.selectOne(queryWrapper);
        return memberReadState;
    }

    /**
     * 更新阅读状态
     *
     * @param memberId  会员编号
     * @param bookId    图书id
     * @param readState 阅读状态
     * @return 阅读状态对象
     */
    @Override
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState=memberReadStateMapper.selectOne(queryWrapper);
        //无则新增，有则更新
        if (memberReadState==null){//如果阅读状态是空说明用户没有选择过任何一个阅读状态
            memberReadState=new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setReadState(readState);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        }else {
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }
        return memberReadState;
    }
}
