package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import com.imooc.reader.utils.MD5Utils;
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
    private  MemberReadStateMapper memberReadStateMapper;

    @Resource
    private EvaluationMapper evaluationMapper;
    /**
     * 会员注册 创建新会员
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */

    public Member createMember(String username, String password, String nickname) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        List<Member> membersList = memberMapper.selectList(queryWrapper);

        //判断用户名是否已存在
        if(membersList.size()>0)
        {
            throw new BussinessException("M01","用户名已存在");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);

      long salt =  new Random().nextInt(1000)+1000;

        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt((long) salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    /**
     * 登录检查
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录对象
     */
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);

        if(member == null)
        {
            throw new BussinessException("M02","用户不存在");
        }
        //Long tempsalt = member.getSalt();
        String md5 = MD5Utils.md5Digest(password, member.getSalt());
        if(!md5.equals(member.getPassword()))
        {
            throw new BussinessException("M03","输入密码错误");
        }
        return member;
    }

    /**
     * 会员阅读状态
     *
     * @param memberId 会员Id
     * @param bookId   书id
     * @return 会员阅读状态
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);

        return memberReadState;
    }

    /**
     * 获取阅读状态
     * @param memberId
     * @param bookId
     * @param readState 状态编号
     * @return 阅读状态对象
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {

        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);

                if(memberReadState == null)
                {
                    memberReadState = new MemberReadState();
                    memberReadState.setMemberId(memberId);
                    memberReadState.setBookId(bookId);
                    memberReadState.setReadState(readState);
                    memberReadState.setCreateTime(new Date());
                    memberReadStateMapper.insert(memberReadState);
                   // return newMemberreadstate;
                }
        else {
                    memberReadState.setReadState(readState);
                    memberReadStateMapper.updateById(memberReadState);
                }
        return  memberReadState;
    }

    /**
     * 发布新的短评
     *
     * @param memberId 会员编号
     * @param bookId   图书编号
     * @param score    评分
     * @param content  短评内容
     * @return 短评对象
     */
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {

        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore((long)score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return  evaluation;
    }

    /**
     * 短评点赞
     *
     * @param evaluationId 短评编写
     * @return 短评对象
     */
    public Evaluation enjoy(Long evaluationId) {

       // QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
       // queryWrapper.eq("evaluation_id",evaluationId);
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy()+1);
        evaluationMapper.updateById(evaluation);
        return  evaluation;
    }
}
