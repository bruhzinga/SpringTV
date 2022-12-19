package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.DTO.SearchFromUser;
import by.zvor.springtv.DTO.VideoInfoToUser;
import by.zvor.springtv.Repository.VideosViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

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

    public Collection<VideoInfoToUser> searchVideos(SearchFromUser searchFromUser) throws SQLException {
        return videosViewRepository.SearchVideos(searchFromUser.getTableName(), searchFromUser.getColumnName(), searchFromUser.getSearchParameters(), searchFromUser.isOracleText());
    }

    public void deleteVideo(int id) throws SQLException {
        videosViewRepository.deleteVideo(id);
    }
}
