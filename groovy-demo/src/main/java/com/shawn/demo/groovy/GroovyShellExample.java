package com.shawn.demo.groovy;

/**
 * @author luxufeng
 * @date 2020-12-8
 **/

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class GroovyShellExample {
    public static void main(String[] args) {
//        loadClassMethod();
        loadClassMethod2();
    }

    static void execExpression() {
        Binding binding = new Binding();
        binding.setVariable("x", 10);
        binding.setVariable("language", "Groovy");

        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate("println \"Welcome to $language\"; y = x * 2; z = x * 3; return x ");

        System.err.println(value + ", " + value.equals(10));
        System.err.println(binding.getVariable("y") + ", " + binding.getVariable("y").equals(20));
        System.err.println(binding.getVariable("z") + ", " + binding.getVariable("z").equals(30));
    }

    static void loadScript() {
        try {
            String[] roots = new String[]{".\\src\\main\\resources\\script\\"};//定义Groovy脚本引擎的根路径
            GroovyScriptEngine engine = new GroovyScriptEngine(roots);
            Binding binding = new Binding();
            binding.setVariable("language", "Groovy");
            Object value = engine.run("SimpleScript.groovy", binding);

            assert value.equals("The End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static void loadClassMethod() {
        try {
            GroovyClassLoader loader = new GroovyClassLoader();
//            Class fileCreator = loader.parseClass(new File("src\\sample\\GroovySimpleFileCreator.groovy"));
            Class fileCreator = loader.parseClass(groovyClass());
            GroovyObject object = (GroovyObject) fileCreator.newInstance();
            System.out.println(object);
            object.invokeMethod("createFile", "E:\\myspace\\learn-center\\groovy-demo\\src\\main\\resources\\a.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void useJSR223() {
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            String HelloLanguage = "def hello(language) {return \"Hello $language\"}";
            engine.eval(HelloLanguage);
            Invocable inv = (Invocable) engine;
            Object[] params = {new String("Groovy")};
            Object result = inv.invokeFunction("hello", params);
            //assert result.equals("Hello Groovy");
            System.out.println(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static String groovyClass() {
        return "class GroovySimpleFileCreator {\n" +
                "    public createFile(String fileName){\n" +
                "        File file = new File(fileName);\n" +
                "        print(fileName);\n" +
                "        file.createNewFile();\n" +
                "    }\n" +
                "}";
    }

    static void loadClassMethod2() {
        try {
            GroovyClassLoader loader = new GroovyClassLoader();
//            Class fileCreator = loader.parseClass(new File("src\\sample\\GroovySimpleFileCreator.groovy"));
            Class fileCreator = loader.parseClass(getScrpitMethod());
            GroovyObject object = (GroovyObject) fileCreator.newInstance();
            System.out.println(object);
            Object result = object.invokeMethod("ratio", 700 * 1024);
            System.out.println("result:" + result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static String getScrpitMethod(){
        return "class ThresholdCalculator {\n" +
                "int ratio(int size) {\n" +
                "        int _1B = 1;\n" +
                "        int _1KB = _1B * 1024;\n" +
                "        int _100KB = _1KB * 100;\n" +
                "        int _500KB = _1KB * 500;\n" +
                "        int _1MB = _1KB * 1024;\n" +
                "        \n" +
                "        if (size > _1MB) {\n" +
                "            //放大访问次数系数\n" +
                "            return 100;\n" +
                "        }\n" +
                "        if (size > _500KB) {\n" +
                "            //放大访问次数系数\n" +
                "            return 5;\n" +
                "        }\n" +
                "        if (size > _100KB) {\n" +
                "            //放大访问次数系数\n" +
                "            return 2;\n" +
                "        }\n" +
                "        return 1;\n" +
                "    }" +
                "}";

    }

}