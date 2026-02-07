package com.ftk.pg.dto;

import lombok.Data;

@Data
public class MerchantDTO {
    private Long mid;
    private String merchantName;
    private String email;
    private String mobileNumber;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String businessCategory;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
//    private LocalDateTime createdDate;
//    private LocalDateTime modifiedDate;
}
