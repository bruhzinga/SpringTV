package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Repository.VideosViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideosViewService {
    private final VideosViewRepository videosViewRepository;

    @Autowired
    public VideosViewService(VideosViewRepository videosViewRepository) {
        this.videosViewRepository = videosViewRepository;
    }

    public void addNewVideo(String name, byte[] video, String type) {
        videosViewRepository.addNewVideo(name, video, type);
    }
}
