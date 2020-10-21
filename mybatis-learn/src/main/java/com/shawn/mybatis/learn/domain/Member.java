package com.shawn.mybatis.learn.domain;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public class Member {
    private Long id;
    private String mobile;

    public Long getId() {
        return id;
    }

    public Member setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public Member setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
