package in.jaaga.learning.bots.languagebot;

import in.jaaga.learning.R;
import in.jaaga.learning.api.Bot;
import in.jaaga.learning.api.ChatItem;

import in.jaaga.learning.bots.languagebot.problems.Noun;
import in.jaaga.learning.bots.skillbot.Mission; // this might be Mission.Mission;
import in.jaaga.learning.bots.skillbot.StringUtil;
import in.jaaga.learning.bots.skillbot.ProblemSkill;



public class LanguageBot extends Bot {
    Mission mission;

    public void init() {
        StringUtil.setResources(getResources());
        mission = new Mission(sender);
        // TODO create a getLanguageContent from StringUtil
        mission.add(new ProblemSkill(this, "learning nouns",  new Noun("The man sat down.", this.context), 1, 100));
    }

    public void onStart() {
        super.onStart();
        init();
        sender.send(new ChatItem(StringUtil.hello()));
        sender.send(mission.getPrompt());
    }

    public void onMessageReceived(String text) {
        mission.processResponse(text);
        sender.send(mission.getPrompt());
    }

    private String title(int resourceId, int arg0) {
        return getResources().getString(resourceId, Integer.valueOf(arg0));
    }

    private String title(int resourceId, int arg0, int arg1) {
        return getResources().getString(resourceId, Integer.valueOf(arg0), Integer.valueOf(arg1));
    }


}