package com.test.absensi.models;

import com.test.absensi.employee.Employee;
import com.test.absensi.user.Profile;
import com.test.absensi.utils.Utils;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class Response {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InitData {
        private String email;
        private String password;
        private Profile profile;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String nin;
        private String fullname;
        private String placeOfBirth;
        private Integer dateOfBirth;
        private String email;
        private String password;
        private Profile profile;
        @Nullable
        private Integer deptId;
        private String deptName;
        @Nullable
        private Integer educationId;
        private String educationName;
        @Nullable
        private Integer jobId;
        private String jobName;
        @Nullable
        private Integer workUnitId;
        private String workUnitName;
        private int sexId;
        private String sexName;
        private String photo;
        private int createdAt;
        private int updatedAt;

        public static Response.User build(Employee employee) {

            return User.builder()
                    .nin(employee.getNin())
                    .fullname(employee.getFullname())
                    .placeOfBirth(employee.getPlaceOfBirth())
                    .dateOfBirth(employee.getDateOfBirth() != null ? Utils.dateToTimestamp(employee.getDateOfBirth()) : null)
                    .email(employee.getEmail())
                    .password(employee.getPassword())
                    .profile(employee.getProfile())
                    .deptId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                    .deptName(employee.getDepartment() != null ? employee.getDepartment().getName() : null)
                    .jobId(employee.getJob() != null ? employee.getJob().getId() : null)
                    .jobName(employee.getJob() != null ? employee.getJob().getName() : null)
                    .educationId(employee.getEducation() != null ? employee.getEducation().getId() : null)
                    .educationName(employee.getEducation() != null ? employee.getEducation().getName() : null)
                    .workUnitId(employee.getWorkUnit() != null ? employee.getWorkUnit().getId() : null)
                    .workUnitName(employee.getWorkUnit() != null ? employee.getWorkUnit().getName() : null)
                    .sexId(employee.getSex().ordinal())
                    .sexName(Sex.values()[(employee.getSex().ordinal())].name())
                    .photo(employee.getPhoto())
                    .createdAt(Utils.dateToTimestamp(employee.getCreatedAt()))
                    .updatedAt(Utils.dateToTimestamp(employee.getUpdatedAt()))
                    .build();
        }

        public static List<Response.User> buildList(List<Employee> list) {
            return list.stream()
                    .map(Response.User::build)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class Error {
        int statusCode;
        String message;
    }

}
