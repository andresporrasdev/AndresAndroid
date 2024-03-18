package algonquin.cst2335.porr0016;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ChatMessageDAO {
    @Insert
    void insertMessage(ChatMessages m);
    @Query("Select * from ChatMessages")
    public List<ChatMessages> getAllMessages();

    @Delete
    void deleteMessages(ChatMessages m);


}
