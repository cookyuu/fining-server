package com.hklim.finingserver.domain.ui.controller;

import com.hklim.finingserver.domain.ui.dto.MainUiDataResponseDto;
import com.hklim.finingserver.domain.ui.service.MainUiService;
import com.hklim.finingserver.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/main")
public class MainController {
    private final MainUiService mainUiService;
    @GetMapping("")
    public ResponseEntity<ResponseDto<MainUiDataResponseDto>> getMainUiData(@AuthenticationPrincipal UserDetails user) {
        return ResponseDto.ok(mainUiService.getMainUiData(user));
    }
}
