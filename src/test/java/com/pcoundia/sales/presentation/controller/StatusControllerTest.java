package com.pcoundia.sales.presentation.controller;
import com.pcoundia.sales.shared.*;
import org.junit.jupiter.api.Test;

public class StatusControllerTest extends BaseIntegrationTests {

@Test
void it_should_status() {
this
.get("/api/v1/status")
.expectStatus().isOk()
.expectBody()
.jsonPath("$.code").isEqualTo(1)
.jsonPath("$.message").isEqualTo("");
}
}
