package com.server.MemberManagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "MAMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_NAME")
    private String username;

    @Column(name = "MEMBER_PASSWORD")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


}
