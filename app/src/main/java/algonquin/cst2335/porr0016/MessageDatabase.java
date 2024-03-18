package algonquin.cst2335.porr0016;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {ChatMessages.class}, version=1)
public abstract class MessageDatabase  extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();
}

