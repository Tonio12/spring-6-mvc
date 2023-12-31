package com.tonio.spring6restmvc.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonio.spring6restmvc.config.SpringSecurityConfig;
import com.tonio.spring6restmvc.model.CustomerDTO;
import com.tonio.spring6restmvc.service.CustomerService;
import com.tonio.spring6restmvc.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.tonio.spring6restmvc.controller.BeerControllerTest.PASSWORD;
import static com.tonio.spring6restmvc.controller.BeerControllerTest.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Import(SpringSecurityConfig.class)
public class CustomerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;


    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void createNewCustomerTest() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .with(httpBasic(USERNAME,  PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
    @Test
    void testCreateCustomerNullCustomerName() throws Exception {
        CustomerDTO dto = CustomerDTO.builder().build();

        given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        MvcResult mvcResult = mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .with(httpBasic(USERNAME,  PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    void listCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .with(httpBasic(USERNAME,  PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3))).andReturn();
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));

        mockMvc.perform(get(CustomerController.Customer_Path_Id, testCustomer.getId())
                        .with(httpBasic(USERNAME,  PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.updateById(any(),any())).willReturn(Optional.of(customer));

        mockMvc.perform(put(CustomerController.Customer_Path_Id, customer.getId())
                        .with(httpBasic(USERNAME,  PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent())
                .andExpect(status().is(204));

        verify(customerService).updateById(uuidArgumentCaptor.capture(), any(CustomerDTO.class));

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(CustomerController.Customer_Path_Id, customer.getId())
                        .with(httpBasic(USERNAME,  PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch(CustomerController.Customer_Path_Id, customer.getId())
                        .with(httpBasic(USERNAME,  PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(CustomerController.Customer_Path_Id, UUID.randomUUID())
                        .with(httpBasic(USERNAME,  PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
