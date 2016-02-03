package org.tsm.nbjaxb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.stream.Stream;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author boris.heithecker
 */
public class JAXBUtil {

    private JAXBUtil() {
    }

    /**
     *
     * @param target A name to identify the annotated types this JAXBContext must know
     * @param other For simplicity, other classes to include into the array, e.g. base types
     * @return
     */
    public static Class[] lookupJAXBTypes(String target, Class... other) {
        return Stream.concat(Arrays.stream(other),
                Lookups.forPath(target + "/JAXBTypes").lookupAll(Class.class).stream())
                .toArray(Class[]::new);
    }

    /**
     *
     */
    @Retention(value = RetentionPolicy.SOURCE)
    @Target(value = {ElementType.TYPE})
    public @interface JAXBRegistration {

        /**
         *
         * @return A name to identify the JAXBContext the annotated type must be known to
         */
        public String target();

    }
}
