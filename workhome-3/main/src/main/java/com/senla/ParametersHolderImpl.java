package com.senla;

import com.senla.annotations.Component;
import com.senla.annotations.Value;

@Component
public class ParametersHolderImpl {
    @Value("db.prop42")
    private String dbProp;

    public String getParameterValue(){
        return dbProp;
    }
}
