package com.D2D.clientBank.account.api.dto;

import com.D2D.clientBank.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {

    @NotNull(message = "Currency cannot be null")
    private Currency currency;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    private Double balance;
}
