package org.wc.utils;

import java.io.File;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class LoadJSObject {
	ScriptEngine engine;
	public LoadJSObject(String filename){
		try{
			ScriptEngineManager manager = new ScriptEngineManager();
			engine = manager.getEngineByName("javascript");
			System.out.println(engine);
			loadJs(filename);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void loadJs(String filename){
		try {
			FileReader re = new FileReader(new File(filename));
			engine.eval(re);
		} catch (Exception e) {

		}
	}

	public Object execute(String name,String...args){
		return engine.get(name);
	}

	public Object executeMethod(String name,Object...args){
		try{
			if(engine instanceof Invocable) {    
				Invocable invoke = (Invocable)engine;
				
				Object obj = invoke.invokeFunction(name,args);  
				return obj;
			}   
		}catch(Exception e){
                    e.printStackTrace();
		}
		return null;
	}

}
