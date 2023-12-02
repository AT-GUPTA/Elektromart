package com.elektrodevs.elektromart;


import com.elektrodevs.elektromart.controller.AccountController;
import com.elektrodevs.elektromart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for AccountController focusing on the passcode change functionality.
 */
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    /**
     * Tests the ChangePasscode method to ensure it handles passcode change requests correctly when new passcode and confirmation match.
     */
    @Test
    public void testChangePasscode_SuccessfulUpdate() throws Exception {
        // Arrange
        String jsonRequest = "{\"oldPasscode\":\"oldPasscode\",\"newPasscode\":\"newPasscode\",\"newPasscodeConfirmation\":\"newPasscode\"}";
        when(userService.updateUserPasscode(anyString(), anyString())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/account/passcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(userService).updateUserPasscode("oldPasscode", "newPasscode");
    }


    /**
     * Tests the ChangePasscode method to ensure it returns an error when new passcode and confirmation do not match.
     */

    @Test
    public void testChangePasscode_FailedUpdateDueToMismatch() throws Exception {
        // Arrange
        String jsonRequest = "{\"oldPasscode\":\"oldPasscode\",\"newPasscode\":\"newPasscode\",\"newPasscodeConfirmation\":\"differentNewPasscode\"}";

        // Act & Assert
        mockMvc.perform(post("/account/passcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnprocessableEntity());

        verify(userService, never()).updateUserPasscode(anyString(), anyString());
    }
}