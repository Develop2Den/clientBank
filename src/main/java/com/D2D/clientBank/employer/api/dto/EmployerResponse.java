package com.D2D.clientBank.employer.api.dto;

import com.D2D.clientBank.customer.api.dto.CustomerResponse;
import com.D2D.clientBank.customer.views.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class EmployerResponse {

    @JsonView(Views.Info.class)
    private Long id;
    @JsonView(Views.Info.class)
    private String name;
    @JsonView(Views.Info.class)
    private String address;
    @JsonView(Views.Info.class)
    @JsonIgnore
    private List<CustomerResponse> customers;
}
