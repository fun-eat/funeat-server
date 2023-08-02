package com.funeat.member.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "05.Member", description = "사용자 기능")
public interface MemberController {

    @Operation(summary = "사용자 정보 조회", description = "사용자 닉네임과 프로필 사진을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "사용자 정보 조회 성공."
    )
    @GetMapping
    ResponseEntity<MemberProfileResponse> getMemberProfile(@AuthenticationPrincipal LoginInfo loginInfo);

    @Operation(summary = "사용자 정보 수정", description = "사용자 닉네임과 프로필 사진을 수정한다.")
    @ApiResponse(
            responseCode = "200",
            description = "사용자 정보 수정 성공."
    )
    @PutMapping
    ResponseEntity<Void> putMemberProfile(@AuthenticationPrincipal LoginInfo loginInfo,
                                          @RequestBody MemberRequest request);
}