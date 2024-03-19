package algonquin.cst2335.porr0016;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.porr0016.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.porr0016.databinding.SentMessageBinding;
import algonquin.cst2335.porr0016.databinding.ReceiveMessageBinding;
import algonquin.cst2335.porr0016.ChatMessageDAO;


public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    ArrayList<ChatMessages> messages;
    ChatRoomViewModel chatModel;
    private RecyclerView.Adapter myAdapter;
    private ChatMessageDAO mDAO; // Declare mDAO here
    ChatMessages removedMessage; // Define removedMessage outside of any method


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_1) {

            new AlertDialog.Builder(this)
                    .setTitle("Delete All Messages")
                    .setMessage("Are you sure you want to delete all messages?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        messages.clear();
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "All messages deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        } else if (id == R.id.about) {
            Toast.makeText(this, "Version 1.0, created by Andres Porras", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void deleteSelectedMessage() {
        if (removedMessage != null) {
            int position = messages.indexOf(removedMessage);
            if (position != -1) {
                messages.remove(position);
                myAdapter.notifyItemRemoved(position);
                Snackbar.make(binding.getRoot(), "Message deleted", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the toolbar as the support action bar
        setSupportActionBar(binding.myToolbar);

        // Create the Room database instance and obtain the DAO
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();


        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });        }


        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            messages.add(new ChatMessages(binding.textInput.getText().toString(), currentDateandTime, true));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            messages.add(new ChatMessages(binding.textInput.getText().toString(), currentDateandTime, false));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                if (viewType == 0) {
                    SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(sendBinding.getRoot(), sendBinding);
                } else {
                    ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(receiveBinding.getRoot(), receiveBinding);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessages chatMessage = messages.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
            @Override
            public int getItemViewType(int position) {
                return messages.get(position).isSentButton() ? 0 : 1;
            }
        });

    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView, SentMessageBinding sendBinding) {
            super(itemView);
            itemView.setOnClickListener(clk->{
                int position =getAbsoluteAdapterPosition();
                removedMessage = messages.get(position); // Store the message that is being removed
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete the message:"+messageText.getText());;
                builder.setTitle("Question");
                builder.setNegativeButton("No", (dialog, cl)->{});
                builder.setPositiveButton("Yes", (dialog, cl)->{
                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position); //adt to myAdapter
                    Snackbar.make(messageText, "You deleted message# "+position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk2 ->{
                                messages.add(position, removedMessage);
                                myAdapter.notifyItemInserted(position);
                            }).show();
                }).create().show();



            });
            messageText = sendBinding.messageText;
            timeText = sendBinding.timeText;
        }

        public MyRowHolder(@NonNull View itemView, ReceiveMessageBinding receiveBinding) {
            super(itemView);
            messageText = receiveBinding.textMessage2;
            timeText = receiveBinding.timeText2;
        }
    }
}



