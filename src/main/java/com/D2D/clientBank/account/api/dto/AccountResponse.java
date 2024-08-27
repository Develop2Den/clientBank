package com.D2D.clientBank.account.api.dto;

import com.D2D.clientBank.customer.views.Views;
import com.D2D.clientBank.enums.Currency;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class AccountResponse {

    @JsonView(Views.Info.class)
    private Long id;
    @JsonView(Views.Info.class)
    private String number;
    @JsonView(Views.Info.class)
    private Currency currency;
    @JsonView(Views.Info.class)
    private Double balance;
    @JsonView(Views.Info.class)
    private Long customerId;
}
