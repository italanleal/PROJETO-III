package br.upe.pojos;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface HelperInterface {
    Logger logger = Logger.getLogger(HelperInterface.class.getName());

    static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0 && !void.class.equals(method.getReturnType());
    }

    static String getSetterName(String getterName) {
        return "set" + getterName.substring(3);
    }

    static <T> void checkout(T source, T destination){
        Method[] methods = source.getClass().getMethods();
        for (Method getter : methods) {
            try {
                if (!HelperInterface.isGetter(getter)) {
                    continue;
                }

                Object value = getter.invoke(source);
                if (value == null) {
                    logger.log(Level.WARNING, "Getter {0} returned null for source: {1}", new Object[]{getter.getName(), source.getClass().getName()});
                }

                for (Method setter : methods) {
                    if (setter.getName().equals(HelperInterface.getSetterName(getter.getName())) && setter.getParameterCount() == 1 && setter.getParameterTypes()[0].isAssignableFrom(getter.getReturnType())) {
                        setter.invoke(destination, value); // Invoca o setter no objeto destination com o valor do getter
                        break;
                    }
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error while checking out: {0}, ERRO: {1}", new Object[]{source.getClass().getName(), e.getMessage()});
            }
        }
    }
}