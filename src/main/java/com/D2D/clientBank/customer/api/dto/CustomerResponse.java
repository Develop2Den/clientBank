package com.D2D.clientBank.customer.api.dto;

import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.customer.views.Views;
import com.D2D.clientBank.employer.api.dto.EmployerResponse;
import com.D2D.clientBank.enums.Role;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class CustomerResponse {

    @JsonView(Views.Info.class)
    private Long id;

    @JsonView(Views.Info.class)
    private String name;

    @JsonView(Views.DetailInfo.class)
    private String email;

    @JsonView(Views.DetailInfo.class)
    private Integer age;

    @JsonView(Views.DetailInfo.class)
    private String phoneNumber;

    @JsonView(Views.DetailInfo.class)
    private Role role;

    @JsonView(Views.Info.class)
    @ToString.Exclude
    private List<AccountResponse> accounts;

    @JsonView(Views.DetailInfo.class)
    @ToString.Exclude
    private List<EmployerResponse> employers;
}
