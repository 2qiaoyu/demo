package com.joham.user;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 添加请求
 *
 * @author joham
 */
@Data
public class UserSaveRequest implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 30, message = "Name should be in the range of 2 and 30 chars")
    private String name;

    @NotNull
    @Min(18)
    private Integer age;
}
