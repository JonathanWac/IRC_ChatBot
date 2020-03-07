import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

public class MyPircBot {
    String botName, login;
    public MyPircBot(){
        Configuration config = new Configuration.Builder()
                .setName("MyPircBot") //Nick of the bot. CHANGE IN YOUR CODE
                .setLogin("PircBotXUser") //Login part of hostmask, eg name:login@host
                .setAutoNickChange(true) //Automatically change nick when the current one is in use
                .addAutoJoinChannel("#pircbotx") //Join #pircbotx channel on connect
                .addServer("irc.freenode.net")
                .addListener(new MyPircBotCommands())
                .buildConfiguration(); //Create an immutable configuration from this builder

        PircBotX myBot = new PircBotX(config);
        try{
            myBot.startBot();
        }
        catch (IOException e){
            System.err.println("An unexpected IOException has occured... Now disconnecting the bot");
            e.printStackTrace();
            myBot.stopBotReconnect();
            myBot.sendIRC().quitServer("An unexpected IOException has occured... Now disconnecting the bot");
        }
        catch (IrcException e){
            System.err.println("An unexpected IrcException has occured... Now disconnecting the bot");
            e.printStackTrace();
            myBot.stopBotReconnect();
            myBot.sendIRC().quitServer("An unexpected IrcException has occured... Now disconnecting the bot");
        }

    }
    public MyPircBot(String botName, String login, String channel, String serverName){
        this.login = login;
        this.botName = botName;
        Configuration config = new Configuration.Builder()
                .setName(botName) //Nick of the bot. CHANGE IN YOUR CODE
                .setLogin(login) //Login part of hostmask, eg name:login@host
                .setAutoNickChange(true) //Automatically change nick when the current one is in use
                .addAutoJoinChannel(channel)//Join #pircbotx channel on connect
                .addServer(serverName)
                .buildConfiguration(); //Create an immutable configuration from this builder

        PircBotX myBot = new PircBotX(config);




    }
}
