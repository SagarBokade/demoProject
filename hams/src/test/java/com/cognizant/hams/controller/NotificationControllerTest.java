package com.cognizant.hams.controller;

import com.cognizant.hams.dto.Response.NotificationResponseDTO;
import com.cognizant.hams.security.CustomUserDetailsService;
import com.cognizant.hams.security.JwtTokenUtil;
import com.cognizant.hams.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    // Mock security beans to allow the test context to load
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    @WithMockUser // Simulate a logged-in user
    public void testGetPatientNotifications() throws Exception {
        // Arrange
        Mockito.when(notificationService.getNotificationForPatient(1L))
                .thenReturn(Collections.singletonList(new NotificationResponseDTO()));

        // Act & Assert
        mockMvc.perform(get("/api/notifications/patients/{patientId}/notification", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    public void testGetNotificationsForDoctor() throws Exception {
        // Arrange
        Mockito.when(notificationService.getNotificationForDoctor(101L))
                .thenReturn(Collections.singletonList(new NotificationResponseDTO()));

        // Act & Assert
        mockMvc.perform(get("/api/notifications/doctors/{doctorId}/notification", 101L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    public void testMarkAsRead() throws Exception {
        // Arrange
        Mockito.doNothing().when(notificationService).markAsRead(1L);

        // Act & Assert
        mockMvc.perform(put("/api/notifications/{notificationId}/read", 1L)
                        .with(csrf())) // Add CSRF token for PUT request
                .andExpect(status().isOk());
    }
}