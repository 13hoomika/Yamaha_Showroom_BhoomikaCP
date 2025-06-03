package com.bcp.yamaha.service.showroom;

import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.repository.showroom.ShowroomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ShowroomServiceImpl implements ShowroomService {

    @Autowired
    ShowroomRepository showroomRepository;

    @Override
    public Boolean addShowroom(ShowroomDto showroomDto) {
        ShowroomEntity showroomEntity = new ShowroomEntity();
        BeanUtils.copyProperties(showroomDto,showroomEntity);
        Boolean addedShowroom = showroomRepository.addShowroom(showroomEntity);
        System.out.println("added showroom entity: " + showroomEntity);
        return addedShowroom;
    }


    @Override
    public List<ShowroomDto> getAllShowroom() {
        List<ShowroomEntity> showroomEntities = showroomRepository.findAllShowroom();
        System.out.println("\nfindAllShowroom from service: " + showroomEntities);

        List<ShowroomDto> dtoList = new ArrayList<>();

        for (ShowroomEntity entity : showroomEntities) {
            ShowroomDto dto = entityToDtoShowroom(entity);
            // OR Calculate bike count from the relationship
            //dto.setBikeCount(entity.getBikes() != null ? entity.getBikes().size() : 0);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private static ShowroomDto entityToDtoShowroom(ShowroomEntity entity) {
        ShowroomDto dto = new ShowroomDto();

        // Manual copy to ensure all fields are set
        dto.setShowroomId(entity.getShowroomId());
        dto.setShowroomName(entity.getShowroomName());
        dto.setShowroomAddress(entity.getShowroomAddress());
        dto.setShowroomPhone(entity.getShowroomPhone());
        dto.setShowroomEmail(entity.getShowroomEmail());
        dto.setShowroomManager(entity.getShowroomManager());
        dto.setBikeCount(entity.getBikeCount());
        dto.setShowroomImg(entity.getShowroomImg());
        return dto;
    }

    @Override
    public Long getTotalShowroomCount() {
        return showroomRepository.countAllShowroom();
    }

    @Override
    public ShowroomDto getShowroomById(Integer showroomId) {
        if (showroomId == null) return null;
        ShowroomEntity entity = showroomRepository.findById(showroomId);
        if (entity != null) {
            ShowroomDto dto = new ShowroomDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        return null;
    }

    @Override
    public boolean existByName(String showroomName) {
        System.out.println("checking showroom name existence in service..........");
        boolean exists = showroomRepository.existByName(showroomName);
        log.info("is emailExist in service: {}", showroomName);
        return exists;
    }

    @Override
    public boolean existByEmail(String email) {
        System.out.println("invoking existByEmail in service..........");
        boolean emailExist = showroomRepository.existByEmail(email);
        System.out.println("is emailExist in service: " + emailExist);
        return emailExist;
    }

    /*@Override
    public void deleteById(int id) {
        showroomRepository.deleteShowroom(id);
    }*/
}
