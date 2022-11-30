package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Repository.VideosViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class VideosViewService {
    private final VideosViewRepository videosViewRepository;

    @Autowired
    public VideosViewService(VideosViewRepository videosViewRepository) {
        this.videosViewRepository = videosViewRepository;
    }

    public int addNewVideo(String name, byte[] video, String type) throws SQLException, ClassNotFoundException {
        return videosViewRepository.addNewVideo(name, video, type);
    }
}
