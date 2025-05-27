package com.example.joohyun.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String shippingFirstName;    // 이름
    private String shippingLastName;     // 성
    private String shippingCompany;      // 회사명
    private String shippingAddress1;     // 주소
    private String shippingAddress2;     // 상세주소
    private String shippingCity;         // 도시
    private String shippingState;        // 주/도
    private String shippingPostcode;     // 우편번호
    private String shippingCountry;      // 국가
    private boolean isDefault;           // 기본 배송지 여부
} 