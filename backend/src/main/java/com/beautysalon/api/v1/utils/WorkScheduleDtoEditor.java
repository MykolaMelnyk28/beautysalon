package com.beautysalon.api.v1.utils;

import com.beautysalon.api.v1.dto.WorkScheduleDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyEditorSupport;
import java.util.List;

public class WorkScheduleDtoEditor extends PropertyEditorSupport {

    private final ObjectMapper objectMapper;

    public WorkScheduleDtoEditor() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try{
            List<WorkScheduleDto> list = objectMapper.readValue(text, new TypeReference<List<WorkScheduleDto>>() {});
            setValue(list);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
