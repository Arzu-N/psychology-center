    package org.example.psychology_center.controller;

    import lombok.RequiredArgsConstructor;
    import org.example.psychology_center.config.CustomUserDetails;
    import org.example.psychology_center.dto.request.SubmitDto;
    import org.example.psychology_center.service.TestService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/test/api/v1")
    public class TestController {

        private final TestService testService;

        @PostMapping("/submit")
        public ResponseEntity<Integer> submitTest(
                @AuthenticationPrincipal CustomUserDetails userDetails,
                @RequestBody SubmitDto dto
        ) {

            int score = testService.submitTest(dto, userDetails.getUser());

            return ResponseEntity.status(HttpStatus.CREATED).body(score);
        }
    }