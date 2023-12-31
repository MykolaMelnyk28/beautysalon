package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.api.v1.dto.ClientDto;
import com.beautysalon.api.v1.dto.FeedbackDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.dto.mapper.base.Mapper;
import com.beautysalon.api.v1.entities.Client;
import com.beautysalon.api.v1.entities.Feedback;

@Mapper
public class FeedbackMapper extends AbstractMapper<Feedback, FeedbackDto> {
    private final AutoMapper<Client, ClientDto> clientMapper;

    public FeedbackMapper(AutoMapper<Client, ClientDto> clientMapper) {
        super();
        this.clientMapper = clientMapper;
    }

    @Override
    protected void postDtoCopy(Feedback source, FeedbackDto destination) {
        destination.setAuthor(clientMapper.toDto(source.getAuthor()));
    }

    @Override
    protected void postEntityCopy(FeedbackDto source, Feedback destination) {
        destination.setAuthor(clientMapper.toEntity(source.getAuthor()));
    }
}
