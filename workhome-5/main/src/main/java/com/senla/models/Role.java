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
public class Role {
    private Long roleId;
    private String roleName;

//   N->M ManyToMany
    private List<Long> privilegeIds;
}
