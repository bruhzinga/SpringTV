package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Repository.ImagesViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesViewService {
    private final ImagesViewRepository imagesViewRepository;

    @Autowired
    public ImagesViewService(ImagesViewRepository imagesViewRepository) {
        this.imagesViewRepository = imagesViewRepository;
    }

    public void addNewImage(String name, byte[] image, String type) {
        imagesViewRepository.addNewImage(name, image, type);
    }

 
}
