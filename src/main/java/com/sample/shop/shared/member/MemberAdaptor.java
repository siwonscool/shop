package com.sample.shop.shared.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.shop.member.domain.MemberStatus;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MemberAdaptor {
    private Long id;
    private String email;
    private String pw;
    private MemberStatus memberStatus;
    private List<String> roles = new ArrayList<>();

    @Setter
    private String errorMessage;

    @Builder
    public MemberAdaptor(Long id, String email, String pw,
        MemberStatus memberStatus, List<String> roles) {
        this.id = id;
        this.email = email;
        this.pw = pw;
        this.memberStatus = memberStatus;
        this.roles = roles;
    }
}
