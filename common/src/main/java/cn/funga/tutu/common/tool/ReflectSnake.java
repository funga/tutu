package cn.funga.tutu.common.tool;

import com.alibaba.fastjson.JSONObject;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.objectweb.asm.*;

/**
 * 反射辅助类
 */
public class ReflectSnake {
    public static JSONObject getMethodParamNames(final Method m) {
        int length = m.getParameterTypes().length;
        JSONObject params = new JSONObject();
        final String n = m.getDeclaringClass().getName();
        ClassReader cr = null;
        try {
            cr = new ClassReader(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM4) {
            @Override
            public MethodVisitor visitMethod(final int access,
                                             final String name, final String desc,
                                             final String signature, final String[] exceptions) {
                final Type[] args = Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数相同
                if (!name.equals(m.getName())
                        || !sameType(args, m.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature,
                            exceptions);
                }
                MethodVisitor v = super.visitMethod(access, name, desc,
                        signature, exceptions);
                return new MethodVisitor(Opcodes.ASM4, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc,
                                                   String signature, Label start, Label end, int index) {

                        int i = index - 1;
                        // 如果是静态方法，则第一就是参数
                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                        if (Modifier.isStatic(m.getModifiers())) {
                            i = index;
                        }
                        if (i >= 0 && i < length) {
                            String[] descarr = desc.split(";")[0].split("/");
                            System.out.println(index+" "+name);
                            params.put(name,descarr[descarr.length-1]);
                        }

                        super.visitLocalVariable(name, desc, signature, start,
                                end, index);
                    }

                };
            }
        }, 0);
        System.out.println(params.toJSONString());
        return params;
    }

    private static boolean sameType(Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }


}
