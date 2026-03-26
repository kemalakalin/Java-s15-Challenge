package com.example.librarysystem.entity.concrete;

import com.example.librarysystem.entity.enums.MemberShipType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Member {
    private Long memberId;
    private final LocalDateTime memberShipDate = LocalDateTime.now();
    private MemberShipType memberShipType;

    public Member(Long memberId, MemberShipType memberShipType) {
        this.setMemberId(memberId);
        this.setMemberShipType(memberShipType);
    }

    public Long getMemberId() { return memberId; }

    public String getMemberShipDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return memberShipDate.format(format);
    }

    public MemberShipType getMemberShipType() { return memberShipType; }

    private void setMemberId(Long memberId) {
        Objects.requireNonNull(memberId, "Member ID cannot be null");
        if (memberId <= 0) throw new IllegalArgumentException("ID must be positive");
        this.memberId = memberId;
    }

    public void setMemberShipType(MemberShipType memberShipType) {
        Objects.requireNonNull(memberShipType, "Membership type cannot be null");
        this.memberShipType = memberShipType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() { return Objects.hash(memberId); }
}