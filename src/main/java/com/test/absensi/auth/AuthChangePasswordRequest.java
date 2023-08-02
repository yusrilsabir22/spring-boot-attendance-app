package com.test.absensi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
