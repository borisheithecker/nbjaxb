/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsm.nbjaxb.impl;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.openide.filesystems.annotations.LayerGeneratingProcessor;
import org.openide.filesystems.annotations.LayerGenerationException;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.tsm.nbjaxb.JAXBUtil;

/**
 *
 * @author boris.heithecker
 */
@ServiceProvider(service = Processor.class)
@SupportedAnnotationTypes("org.tsm.nbjaxb.JAXBUtil.JAXBRegistration")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JAXBClassRegistrationLayerGenerator<C> extends LayerGeneratingProcessor {

    @Override
    protected boolean handleProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) throws LayerGenerationException {
        if (roundEnv.processingOver()) {
            return false;
        }
        for (Element e : roundEnv.getElementsAnnotatedWith(JAXBUtil.JAXBRegistration.class)) {
            JAXBUtil.JAXBRegistration r = e.getAnnotation(JAXBUtil.JAXBRegistration.class);
            writeOneFile(r, e);
        }
        return true;
    }

    protected void writeOneFile(JAXBUtil.JAXBRegistration r, Element e) throws LayerGenerationException, IllegalArgumentException {
        String componentPath = r.target() + "/JAXBTypes";
        String clazz = processingEnv.getElementUtils().getBinaryName((TypeElement) e).toString();
        String basename = clazz.replace('.', '-');
        String file = componentPath + "/" + basename + ".instance";
        layer(e).file(file)
                .methodvalue("instanceCreate", JAXBClassRegistrationLayerGenerator.class.getName(), "createJAXBType")
                .stringvalue("jaxbType", clazz)
                .write();
    }

    public static <C> Class<C> createJAXBType(Map<String, ?> params) {
        String type = (String) params.get("jaxbType");
        try {
            final ClassLoader sysCl = Lookup.getDefault().lookup(ClassLoader.class);
            return (Class<C>) Class.forName(type, true, sysCl);
        } catch (ClassNotFoundException | ClassCastException ex) {
            Logger.getLogger(JAXBClassRegistrationLayerGenerator.class.getName()).log(Level.SEVERE, "An error ocurred looking up class {0}.", type);
        }
        return null;
    }
}
