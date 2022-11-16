package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Repository.ImagesViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class ImagesViewService {
    private final ImagesViewRepository imagesViewRepository;

    @Autowired
    public ImagesViewService(ImagesViewRepository imagesViewRepository) {
        this.imagesViewRepository = imagesViewRepository;
    }

    public void addNewImage(String name, byte[] image, String type) throws SQLException, ClassNotFoundException {
        imagesViewRepository.addNewImage(name, image, type);
    }


    @Transactional
    public byte[] getThumbnail(Long movieId) throws SQLException, ClassNotFoundException {
        return imagesViewRepository.getThumbnail(movieId);


    }
}
