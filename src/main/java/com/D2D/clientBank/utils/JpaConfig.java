package com.D2D.clientBank.utils;

import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.account.db.Account;
import com.D2D.clientBank.customer.api.dto.CustomerResponse;
import com.D2D.clientBank.customer.db.Customer;
import com.D2D.clientBank.employer.api.dto.EmployerResponse;
import com.D2D.clientBank.employer.db.Employer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@Configuration
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class JpaConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Customer, CustomerResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setEmail(source.getEmail());
                map().setAge(source.getAge());
                map().setPhoneNumber(source.getPhoneNumber());
            }
        });

        modelMapper.addMappings(new PropertyMap<Account, AccountResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setNumber(source.getNumber());
                map().setCurrency(source.getCurrency());
                map().setBalance(source.getBalance());
                map().setCustomerId(source.getCustomer().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Employer, EmployerResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setAddress(source.getAddress());
            }
        });

        modelMapper.validate();

        return modelMapper;
    }
}
