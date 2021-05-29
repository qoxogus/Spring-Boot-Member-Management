package com.server.MemberManagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long idx;

    @Column(name = "MAMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_NAME")
    private String username;

    @Column(name = "MEMBER_ID")
    private String id;

    @Column(name = "MEMBER_PASSWORD")
    private String password;

}
