package NLPbot;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.TokenizerME;

public class TokenInitialzer {
    static InputStream modelInToken;
    static TokenizerModel modelToken;
    AssetManager assetManager;

    public InputStream getSourceModel(Context context) {


        try {
            assetManager = context.getAssets();
            
            modelInToken = assetManager.open("en-token.bin");
            //modelInToken = new FileInputStream("/src/main/assets/en-token.bin");
            //AssetFileDescriptor fileDescriptor = assetManager.openFd("en-token.bin");
            //AssetFileDescriptor assetFileDescriptor = assetManager.openFd("en-token.bin");  
            //FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            //modelInToken = new FileInputStream(fileDescriptor);
            

        }
        catch (FileNotFoundException e) {
            System.out.println("something went wrong" + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelInToken;
    }

    public TokenizerModel createTokenModel(InputStream modelInToken) {
        try {
            modelToken = new TokenizerModel(modelInToken);
            System.out.println(modelToken);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (modelInToken != null) {
                try {
                    modelInToken.close();
                }
                catch (IOException e) {
                }
            }
        }
        return modelToken;
    }
}