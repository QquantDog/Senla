package com.senla.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilege {
    private Long privilegeId;
    private String privilegeName;
    private String description;

    private List<Long> roleIds;
}
