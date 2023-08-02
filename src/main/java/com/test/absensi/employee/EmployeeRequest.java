package com.test.absensi.employee;

import com.test.absensi.user.Profile;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {

    private String nin;
    private String fullname;
    @Email
    private String email;
    private Profile profile;
    private String placeOfBirth;
    private Integer dateOfBirth;
    private Integer sexId;
    private Integer educationId;
    private Integer jobId;
    private Integer deptId;
    private Integer workUnitId;
    private String password;

}
