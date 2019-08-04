package com.ss.chatbot;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.ss.chatbot.model.ChatEntity;
import com.ss.chatbot.model.NewChatEntity;
import com.ss.chatbot.respository.ChatRepository;
import com.ss.chatbot.respository.NewChatRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
	@Test
	public void useAppContext() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();

		assertEquals("com.ss.chatbot", appContext.getPackageName());
	}

	private ChatRepository chatRepository;
	private NewChatRepository newChatRepository;


	@Test
	public void insert() {
		Context appContext = InstrumentationRegistry.getTargetContext();
		chatRepository = new ChatRepository(appContext);
		newChatRepository = new NewChatRepository(appContext);

		String name = "Shubham Singhal";
		String chatBotName = "Walter White";
		String chatBotID = "090SBN";
		String type = "Sender";
		String nameID = "Shubham0712";
		boolean isSynved = false;
		String message = "Mr. White, STAY OUT OF MY TERRITORY.";
		String dateTime = "09:41 AM";

		NewChatEntity newChatEntity = new NewChatEntity();
		newChatEntity.setName(name);
		newChatEntity.setNameID(nameID);

		newChatRepository.insert(newChatEntity);

		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setMessage(message);
		chatEntity.setSenderName(name);
		chatEntity.setNameID(nameID);
		chatEntity.setChatBotName(chatBotName);
		chatEntity.setChatBotID(chatBotID);
		chatEntity.setDateTime(dateTime);
		chatEntity.setType(type);
		chatEntity.setSynced(isSynved);

		chatRepository.insert(chatEntity);
	}
}
