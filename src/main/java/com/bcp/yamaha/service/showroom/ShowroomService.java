package com.bcp.yamaha.service.showroom;

import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.entity.ShowroomEntity;

import java.util.List;

public interface ShowroomService {
    Boolean addShowroom(ShowroomDto showroomDto);
    List<ShowroomDto> getAllShowroom();
    Long getTotalShowroomCount();

    ShowroomDto getShowroomById(Integer showroomId);
}
