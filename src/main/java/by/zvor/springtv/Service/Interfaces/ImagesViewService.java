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

    public byte[] getPersonImage(Long personId) throws SQLException, ClassNotFoundException {
        return imagesViewRepository.getPersonImage(personId);
    }

    public void deleteImage(Long id) throws SQLException, ClassNotFoundException {
        imagesViewRepository.deleteImage(id);
    }

    public void updateImage(Long id, String name, byte[] image, String type) throws SQLException, ClassNotFoundException {
        imagesViewRepository.updateImage(id, name, image, type);
    }
}
