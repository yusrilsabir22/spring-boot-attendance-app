package com.test.absensi.models;

import com.test.absensi.user.Profile;
import jakarta.validation.constraints.Email;
import lombok.*;

public class Request {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InitData {
        @NonNull
        private String adminName;
        @NonNull
        private String company;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        @Email
        @NonNull
        private String email;
        @NonNull
        private String password;
        @NonNull
        private Profile profile;
    }

}
