package org.car_rantel.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
}
