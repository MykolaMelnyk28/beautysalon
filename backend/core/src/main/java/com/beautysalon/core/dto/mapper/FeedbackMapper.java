package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.ClientDto;
import com.beautysalon.core.dto.EmployeeDto;
import com.beautysalon.core.dto.FeedbackDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Administrator;
import com.beautysalon.core.entities.Client;
import com.beautysalon.core.entities.Feedback;
import com.beautysalon.core.entities.Master;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper extends AbstractMapper<Feedback, FeedbackDto> {

    private AutoMapper<Client, ClientDto> clientMapper;
    private AutoMapper<Master, EmployeeDto> masterMapper;
    private AutoMapper<Administrator, EmployeeDto> administratorMapper;

    public FeedbackMapper(
            AutoMapper<Client, ClientDto> clientMapper,
            AutoMapper<Master, EmployeeDto> masterMapper,
            AutoMapper<Administrator, EmployeeDto> administratorMapper
    ) {
        super();
        this.clientMapper = clientMapper;
        this.masterMapper = masterMapper;
        this.administratorMapper = administratorMapper;
    }

    @Override
    protected void postDtoCopy(Feedback source, FeedbackDto destination) {
        destination.setAuthor(clientMapper.toDto(source.getAuthor()));

        if (source.getMaster() != null) {
            destination.setRecipient(masterMapper.toDto(source.getMaster()));
        } else if (source.getAdministrator() != null) {
            destination.setRecipient(administratorMapper.toDto(source.getAdministrator()));
        }
    }

    @Override
    protected void postEntityCopy(FeedbackDto source, Feedback destination) {

        destination.setAuthor(clientMapper.toEntity(source.getAuthor()));
        if (source.getRecipient() != null) {
            final String position = source.getRecipient().getPosition();
            if ("master".equals(position)) {
                destination.setMaster(masterMapper.toEntity(source.getRecipient()));
            } else if ("administrator".equals(position)) {
                destination.setAdministrator(administratorMapper.toEntity(source.getRecipient()));
            }
        }

    }
}
