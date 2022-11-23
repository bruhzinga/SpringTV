package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Repository.HistoryViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class HistoryViewService {

    private final HistoryViewRepository historyViewRepository;

    private final UserViewService userService;

    @Autowired
    public HistoryViewService(HistoryViewRepository historyViewRepository, UserViewService userService) {
        this.historyViewRepository = historyViewRepository;
        this.userService = userService;
    }

    public void addHistory(String username, int id) throws SQLException, ClassNotFoundException {

        var userId = userService.GetUserIdByLogin(username);
        historyViewRepository.addHistory(userId, id);

    }
}
