package nl.hkstwk.spring.rest_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private OrderModelAssembler orderModelAssembler;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(new OrderController(orderRepository, orderModelAssembler))
                        .setControllerAdvice(new OrderNotFoundAdvice())
                        .build();
    }

    @Test
    void one() throws Exception {
        Order order = new Order("MacBook", Status.IN_PROGRESS);
        long id = 45L;

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isEmpty())
                .andExpect(jsonPath("$.description").value(order.getDescription()))
                .andExpect(jsonPath("$.status").value(order.getStatus().toString()))
                .andDo(print());
    }

    @Test
    void oneNotFound() throws Exception {
        long id = 32L;
        final OrderNotFoundException exception = new OrderNotFoundException(id);

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        final ResultActions result = mockMvc.perform(get("/orders/{id}", id));
        result.andExpect(status().isNotFound());
        result.andExpect(r -> assertTrue(r.getResolvedException() instanceof OrderNotFoundException));
        result.andExpect(r -> assertEquals(r.getResolvedException().getMessage(),exception.getMessage()));
        result.andDo(print());


    }
}

