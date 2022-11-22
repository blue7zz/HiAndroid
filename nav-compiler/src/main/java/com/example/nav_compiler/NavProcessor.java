package com.example.nav_compiler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nav_annotation.Destination;
import com.google.auto.service.AutoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


/**
 *
 * 主要为了解析注解类，存储相应的信息
 *
 * https://github.com/google/auto/tree/master/service [AutoService]
 * <p>
 * <p>
 * SupportedAnnotationTypes
 * 用来指示注释处理器支持哪些注释类型的注释。
 * Processor.getSupportedAnnotationTypes() 方法可以根据此注释的值构造它的结果，正如 AbstractProcessor.getSupportedAnnotationTypes() 所做的一样。
 * 只有符合语法的字符串才应该作为值来使用。
 */
@AutoService(Processor.class) //自定义注解处理器注册才能被Java虚拟机调用 这个是自动注册
@SupportedSourceVersion(SourceVersion.RELEASE_8) //支持java 1.8版本
@SupportedAnnotationTypes("com.example.nav_annotation.Destination") //对应注解类
public class NavProcessor extends AbstractProcessor {

    private static final String PAGE_TYPE_ACTIVITY = "Activity";
    private static final String PAGE_TYPE_FRAGMENT = "Fragment";
    private static final String PAGE_TYPE_DIALOG = "Dialog";
    private static final String OUTPUT_FILE_NAME = "destination.json";

    private Messager messager;
    private Filer filer;


    /**
     * 初始化时候
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "enter init....");

        //????
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //获取使用了Destination所有的注解
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Destination.class);
        if (!elements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(elements, Destination.class, destMap);

            //在 app/main/assets中 把解析到的注解信息写入进文件中
            try {
                FileObject resource =filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME);
                // /app/build/intermediates/javac/debug/classes/目录下
                //app/main/assets/
                String resourcePath = resource.toUri().getPath();
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
                String assetsPath = appPath + "src/main/assets";


                File file = new File(assetsPath);
                //TODO 如果已存在 为什么要创建?
                if(file.exists()){
                    file.mkdirs();
                }
                String content = JSON.toJSONString(destMap);
                File outPutFile = new File(assetsPath,OUTPUT_FILE_NAME);

                //如果文件已经存在删除它
                if(outPutFile.exists()){
                    outPutFile.delete();
                }
                outPutFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(outPutFile);
                OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                writer.write(content);
                writer.flush();

                fileOutputStream.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        return false;
    }

    private void handleDestination(Set<? extends Element> elements, Class<Destination> destinationClass, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element; //单个注解元素的所有信息
            String clazName = typeElement.getQualifiedName().toString();//全类名
            Destination annotation = typeElement.getAnnotation(destinationClass); //获取注解相关信息

            String pageUrl = annotation.pageUrl();                      //pageUrl
            boolean asStarter = annotation.asStarter();                 //asStarter
            int id = Math.abs(clazName.hashCode());                     //生成ID

            String destType = getDestinationType(typeElement);          //获取被注解的类型

            //是否包含两个相同的
            if(destMap.containsKey(pageUrl)){
                messager.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的pageUrl:" + pageUrl);
            }else{
                //解析
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("clazName", clazName);
                jsonObject.put("pageUrl", pageUrl);
                jsonObject.put("asStarter", asStarter);
                jsonObject.put("id", id);
                jsonObject.put("destType", destType);
                destMap.put(pageUrl, jsonObject);
            }

        }
    }

    /**
     * 获取被注解的类的类型
     *
     * @param typeElement
     * @return
     */
    private String getDestinationType(TypeElement typeElement) {
        TypeMirror typeMirror = typeElement.getSuperclass();
        String superClazName = typeMirror.toString();
        if (superClazName.contains(PAGE_TYPE_ACTIVITY.toLowerCase())) {
            return PAGE_TYPE_ACTIVITY.toLowerCase();
        } else if (superClazName.contains(PAGE_TYPE_FRAGMENT.toLowerCase())) {
            return PAGE_TYPE_FRAGMENT.toLowerCase();

        } else if (superClazName.contains(PAGE_TYPE_DIALOG.toLowerCase())) {
            return PAGE_TYPE_DIALOG.toLowerCase();
        }

        //这个父类的类型是类的类型，或者是接口的类型
        //这个还不是太明白，应用的场景到底是什么
        if(typeMirror instanceof DeclaredType){
            Element element = ((DeclaredType) typeMirror).asElement();
            if(element instanceof TypeElement){
                return getDestinationType((TypeElement)element );
            }
        }

        return null;
    }
}