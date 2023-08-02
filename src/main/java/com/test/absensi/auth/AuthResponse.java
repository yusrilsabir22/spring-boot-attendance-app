package com.test.absensi.auth;

import com.test.absensi.employee.Employee;
import com.test.absensi.utils.Utils;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    public Result result;

    @Data
    @Builder
    public static class Result {
        private String token;
        private String refreshToken;
        private Info info;
    }

    @Data
    @Builder
    public static class Info {
        private String profile;
        private String nin;
        private String fullname;
        private String email;

        @Nullable
        private String placeOfBirth;
        @Nullable
        private Integer dateOfBirth;
        @Nullable
        private Integer sexId;
        @Nullable
        private Integer educationId;
        @Nullable
        private Integer jobId;
        @Nullable
        private Integer deptId;
        @Nullable
        private Integer workUnitId;
        @NotNull
        private String password;
        @Nullable
        private String photo;

        public static Info build(Employee employee) {
            return Info.builder()
                    .sexId(employee.getSex().ordinal())
                    .deptId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                    .fullname(employee.getFullname())
                    .workUnitId(employee.getWorkUnit() != null ? employee.getWorkUnit().getId() : null)
                    .dateOfBirth(employee.getDateOfBirth() != null ? Utils.dateToTimestamp(employee.getDateOfBirth()) : null)
                    .placeOfBirth(employee.getPlaceOfBirth())
                    .educationId(employee.getEducation() != null ? employee.getEducation().getId() : null)
                    .jobId(employee.getJob() != null ? employee.getJob().getId() : null)
                    .nin(employee.getNin())
                    .profile(employee.getProfile().name())
                    .email(employee.getEmail())
                    .password(employee.getPassword())
                    .photo(employee.getPhoto())
                    .build();
        }
    }
}
