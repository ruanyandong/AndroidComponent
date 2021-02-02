package com.wings.annotation.compiler;

import com.google.auto.service.AutoService;
import com.wings.annotation.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author -> Wings
 * @date -> 2020/8/28
 * @email -> ruanyandongai@gmail.com
 * 729368173@qq.com
 * @phone -> 18983790146
 * @blog -> https://ruanyandong.github.io
 * -> https://blog.csdn.net/qq_34681580
 *
 * 注解处理器
 * 1、找到代码中用到某些注解的东西
 * 2、找到之后，获取到注解中的key，以及注解所标记的类 生成代码
 */
//@SupportedSourceVersion(value = SourceVersion.RELEASE_7)
//@SupportedAnnotationTypes(value = {"com.wings.annotation.Route"})
@AutoService(Processor.class) //注册注解处理器
public class AnnotationCompiler extends AbstractProcessor {

    // 获取一个生成文件的对象
    Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();

    }

    /**
     * 声明注解处理器支持的Java版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();//SourceVersion.latestSupported()
    }

    /**
     * 声明注解处理器要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }

    /**
     * 整个注解处理器的核心，在程序执行的时候，回去执行方法里面的业务逻辑
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // 获取到Route标记的类节点
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Route.class);
        Map<String,String> map = new HashMap<>();

        for (Element element:elements) {
            TypeElement typeElement = (TypeElement) element;
            String activityName = typeElement.getQualifiedName().toString();//包名加类名 比如 com.wings.annotation.Route
            String key = typeElement.getAnnotation(Route.class).value();
            map.put(key,activityName+".class");
        }

        if (map.size() > 0){

            Writer writer = null;

            // 创建java文件
            try {
                // 防止生成的类名冲突
                String utilName = "DRouter"+System.currentTimeMillis();
                // 生成Java文件
                JavaFileObject sourceFile = mFiler.createSourceFile("com.wings.util."+utilName);

                writer = sourceFile.openWriter();

                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append("package com.wings.ARouter;\n");
                stringBuffer.append("import com.wings.router.IRouter;\n");
                stringBuffer.append("import com.wings.router.Router;\n");
                stringBuffer.append("public class "+utilName+" implements IRouter {\n");
                stringBuffer.append("@Override\n");
                stringBuffer.append("public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    String activityName = map.get(key);
                    stringBuffer.append(" Router.getInstance().addActivity("+"\""+key+"\""+","+activityName+");");
                }
                stringBuffer.append("}\n");
                stringBuffer.append("}\n");

                writer.write(stringBuffer.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (writer != null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }
}
