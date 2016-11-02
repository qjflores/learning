package NLPbot;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
import android.util.Log;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class PosTaggerInitializer {
	static InputStream modelInPOStagger;
	static POSModel modelPOStagger;
    AssetManager assetManager;

	public InputStream getSourceModel(Context context) {

        try {
            assetManager = context.getAssets();
            modelInPOStagger = assetManager.open("en-pos-maxent.bin");
            //modelInToken = new FileInputStream("/src/main/assets/en-pos-maxent.bin");
            //AssetFileDescriptor fileDescriptor = assetManager.openFd("en-pos-maxent.bin");
            //modelInPOStagger = fileDescriptor.createInputStream();

        }
        catch (FileNotFoundException e) {
            System.out.println("something went wrong" + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelInPOStagger;

    }

    public POSModel createPosModel(InputStream modelInPOStagger) {
    	try {
                modelPOStagger = new POSModel(modelInPOStagger);
            }
            catch (IOException e) {
              // Model loading failed, handle the error
              e.printStackTrace();
              Log.e("createPosModel", "aoeu;nqjk;q");
            }
            finally {
              if (modelInPOStagger != null) {
                try {
                  modelInPOStagger.close();
                }
                catch (IOException e) {
                }
              }
            }
        return modelPOStagger;
    }
}