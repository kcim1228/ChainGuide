package edu.ubbcluj.backend.repository;

import java.util.List;

import edu.ubbcluj.backend.model.Messages;
import edu.ubbcluj.backend.model.Users;

public interface MessagesDAO {
	Messages insertMessage(Messages m);
	void deleteMessage(Messages m);
	void updateMessage(Messages m);
	List<Messages> getAllMessagesByReceiver(Users u);
	List<Messages> getAllMessagesBySender(Users u);
	List<Messages> getAllUnreadMessagesBySender(Users u);
	List<Messages> getAllReadMessagesBySender(Users u);
	

}
