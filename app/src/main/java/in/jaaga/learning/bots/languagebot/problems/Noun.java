package in.jaaga.learning.bots.languagebot.problems;

import android.content.Context;

import java.util.*;
import java.io.InputStream;
import java.io.FileInputStream;

import in.jaaga.learning.bots.skillbot.Problem;
import in.jaaga.learning.bots.skillbot.problems.SimpleProblem;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import NLPbot.TokenInitialzer;
import NLPbot.PosTaggerInitializer;
import penntree.PennTree;

public class Noun extends SimpleProblem {
    String sentence;
    List not_answer_list;
    static InputStream modelIn;
    static InputStream modelInToken;
    static InputStream modelInPOStagger;
    static TokenizerModel modelToken;
    static POSModel modelPOStagger;
    static TokenInitialzer tokenInitialzer;
    static PosTaggerInitializer posTaggerInitializer;
    Context context;
    //static String prompt = "";
    public Noun(String sentence, Context context) {
        this.context = context;
        //initialize stuff
        tokenInitialzer = new TokenInitialzer();
        modelInToken = tokenInitialzer.getSourceModel(context);
        //create a new model
        modelToken = tokenInitialzer.createTokenModel(modelInToken);

        // setup part of speech model and part of speech model

        posTaggerInitializer = new PosTaggerInitializer();
        modelInPOStagger = posTaggerInitializer.getSourceModel(context);

        // cerate new part of speech model
        modelPOStagger = posTaggerInitializer.createPosModel(modelInPOStagger);
        System.out.println(modelInPOStagger);
        Tokenizer tokenizer = new TokenizerME(modelToken);
        POSTaggerME tagger = new POSTaggerME(modelPOStagger);

        HashMap problemMap = new HashMap();
        List pos_list = new ArrayList();
        ArrayList answer_list = new ArrayList();
        not_answer_list = new ArrayList();
        //get tokens and tags
        String[] tokens = tokenizer.tokenize(sentence);
        String tags[] = tagger.tag(tokens);
        //get penn tree hashmaps
        PennTree penntree = new PennTree();
        HashMap penntree_hashmap = penntree.getPennTreeHashMap();
        HashMap noun_hashmap = penntree.getPennTreeNouns();
        prompt += "Which is the noun?";
        for (int i = 0; i<tokens.length; i++)
            {
            HashMap mMap = new HashMap();
            mMap.put("token", tokens[i]);
            mMap.put("tag", tags[i]);
            if (null != penntree_hashmap.get(tags[i])) {
                if (null != noun_hashmap.get(tags[i])) {
                    if (answer_list.size() == 0) {
                        //answer_list.add(tokens[i]);
                        answer = tokens[i];
                    }
                }
                else {
                    if (not_answer_list == null) {
                        not_answer_list.add(tokens[i]);
                    }
                    else if (not_answer_list.size() < 3) {
                        not_answer_list.add(tokens[i]);
                    }
                }
                mMap.put("pos", penntree_hashmap.get(tags[i]));
                //check if the tag is a noun
            }
            else {
                mMap.put("pos", "null1");    
            }
            pos_list.add(mMap);
        }
    }
    
    public Problem next() {
        return new Noun("The woman sat down.", context);
    }
/*    public static HashMap<String, String> createNounProblem(String sentence1, Tokenizer tokenizer, POSTaggerME tagger) {
        //initialize stuff
        HashMap problemMap = new HashMap();
        List pos_list = new ArrayList();
        ArrayList answer_list = new ArrayList();
        List not_answer_list = new ArrayList();
        //get tokens and tags
        String[] tokens = tokenizer.tokenize(sentence1);
        String tags[] = tagger.tag(tokens);
        //get penn tree hashmaps
        PennTree penntree = new PennTree();
        HashMap penntree_hashmap = penntree.getPennTreeHashMap();
        HashMap noun_hashmap = penntree.getPennTreeNouns();
        prompt += "Which is the noun?";
        //iterate over the tokens
        for (int i = 0; i<tokens.length; i++)
        {
            HashMap mMap = new HashMap();
            mMap.put("token", tokens[i]);
            mMap.put("tag", tags[i]);
            if (null != penntree_hashmap.get(tags[i])) {
                if (null != noun_hashmap.get(tags[i])) {
                    if (answer_list.size() == 0) {
                        answer_list.add(tokens[i]);
                    }
                }
                else {
                    if (not_answer_list == null) {
                        not_answer_list.add(tokens[i]);
                    }
                    else if (not_answer_list.size() < 3) {
                        not_answer_list.add(tokens[i]);
                    }
                }
                mMap.put("pos", penntree_hashmap.get(tags[i]));
                //check if the tag is a noun
            }
            else {
                mMap.put("pos", "null1");    
            }
            pos_list.add(mMap);
        }

        problemMap.put("prompt", prompt);
        problemMap.put("answer_list", answer_list.get(0));
        problemMap.put("not_answer_list", not_answer_list);
        return problemMap;
        //return prompt; //TODO add answer list 
    }*/

}