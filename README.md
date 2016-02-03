# nbjaxb


How to use:
Annotate classes you want to include into some JAXBContext. This class can be located anywhere, in any module.  

@JAXBUtil.JAXBRegistration(target = "JAXBContextName")
@XmlRootElement(name = "file-root", namespace = "http://some-namespace.xsd")
public class SomeTypeExt extends SomeJAXBType {

}



To create a JAXBContext which knows this registered class, simply do: 

final Class[] clz = JAXBUtil.lookupJAXBTypes("JAXBContextName", SomeJAXBType.class); 
JAXBContext ctx;
try {
    ctx = JAXBContext.newInstance(clz);
} 
catch (JAXBException ex) {
    .....
}

You don't need a module dependency on the other module containing the registered type. 
