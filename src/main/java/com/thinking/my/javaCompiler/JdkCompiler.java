package com.thinking.my.javaCompiler;

import com.google.common.collect.Lists;

import javax.tools.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author liyong
 * @Date 2021/8/20 3:57 下午
 **/
public final class JdkCompiler {
    static DiagnosticCollector<JavaFileObject> DIAGNOSTIC_COLLECTOR = new DiagnosticCollector<>();

    @SuppressWarnings("unchecked")
    public static <T> T compile(String packageName,
                                String className,
                                String sourceCode,
                                Class<?>[] constructorParamTypes,
                                Object[] constructorParams) throws Exception {
        // 获取系统编译器实例
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 设置编译参数
        List<String> options = new ArrayList<>();
        options.add("-source");
        options.add("1.8");
        options.add("-target");
        options.add("1.8");
        // 获取标准的Java文件管理器实例
        StandardJavaFileManager manager = compiler.getStandardFileManager(DIAGNOSTIC_COLLECTOR, null, null);
        // 初始化自定义类加载器
        JdkDynamicCompileClassLoader classLoader = new JdkDynamicCompileClassLoader(Thread.currentThread().getContextClassLoader());
        // 初始化自定义Java文件管理器实例
        JdkDynamicCompileJavaFileManager fileManager = new JdkDynamicCompileJavaFileManager(manager, classLoader);
        String qualifiedName = packageName + "." + className;
        // 构建Java源文件实例
        CharSequenceJavaFileObject javaFileObject = new CharSequenceJavaFileObject(className, sourceCode);
        // 添加Java源文件实例到自定义Java文件管理器实例中
        fileManager.addJavaFileObject(
                StandardLocation.SOURCE_PATH,
                packageName,
                className + CharSequenceJavaFileObject.JAVA_EXTENSION,
                javaFileObject
        );
        // 初始化一个编译任务实例
        JavaCompiler.CompilationTask compilationTask = compiler.getTask(
                null,
                fileManager,
                DIAGNOSTIC_COLLECTOR,
                options,
                null,
                Lists.newArrayList(javaFileObject)
        );
        Boolean result = compilationTask.call();
        System.out.println(String.format("编译[%s]结果:%s", qualifiedName, result));
        Class<?> klass = classLoader.loadClass(qualifiedName);
        return (T) klass.getDeclaredConstructor(constructorParamTypes).newInstance(constructorParams);
    }
}
