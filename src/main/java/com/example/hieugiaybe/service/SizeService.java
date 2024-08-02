package com.example.hieugiaybe.service;

import com.example.hieugiaybe.dto.ColorAndSizeDto;
import com.example.hieugiaybe.entities.Color;
import com.example.hieugiaybe.entities.Size;
import com.example.hieugiaybe.exception.EmptyDataException;
import com.example.hieugiaybe.exception.NotFoundException;
import com.example.hieugiaybe.repository.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public ColorAndSizeDto getSizeById(Integer id){
        Optional<Size> checkingSize = sizeRepository.findById(id);
        if (checkingSize.isEmpty()){
            throw new NotFoundException("Size does not exist!");
        }
        Size checkedSize = checkingSize.get();
        ColorAndSizeDto response = new ColorAndSizeDto(
                checkedSize.getId(),
                checkedSize.getTitle()
        );
        return response;
    }

    public List<ColorAndSizeDto>getAllSize(){
        List<Size> listSize = sizeRepository.findAll();
        if (listSize.isEmpty()){
            throw new EmptyDataException("Size list does not exist!");
        }
        List<ColorAndSizeDto> response = new ArrayList<>();
        for (Size size : listSize){
            ColorAndSizeDto newSize = new ColorAndSizeDto(
                    size.getId(),
                    size.getTitle()
            );
            response.add(newSize);
        }
        return response;
    }

    public ColorAndSizeDto createSize(ColorAndSizeDto colorAndSizeDto){
        Size newSize = new Size(
                colorAndSizeDto.getId(),
                colorAndSizeDto.getTitle(),
                null
        );
        Size savedSize = sizeRepository.save(newSize);
        ColorAndSizeDto response = new ColorAndSizeDto(
                savedSize.getId(),
                savedSize.getTitle()
        );
        return response;
    }

    public ColorAndSizeDto updateSizeById(Integer id, ColorAndSizeDto colorAndSizeDto){
        Size checkingSize = sizeRepository.findById(id).orElseThrow(()->new NotFoundException("Size does not exist!"));
        checkingSize.setId(colorAndSizeDto.getId());
        checkingSize.setTitle(colorAndSizeDto.getTitle());
        Size savedSize = sizeRepository.save(checkingSize);
        ColorAndSizeDto response = new ColorAndSizeDto(
                savedSize.getId(),
                savedSize.getTitle()
        );
        return response;
    }

    public String deleteSizeById(Integer id){
        Size checkingSize = sizeRepository.findById(id).orElseThrow(()->new NotFoundException("Size does not exist!"));
        Integer sizeId = checkingSize.getId();
        sizeRepository.deleteById(sizeId);
        return "Size deleted By Id: " + sizeId;
    }
}
