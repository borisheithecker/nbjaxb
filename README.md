# nbjaxb

Facilitates the creation of JAXBContext instances in NetBeans RCP. Classes in implementation modules can be registered for JAXBContext creation in System FS. 

How to use:

Annotate classes you want to include into some JAXBContext. These can be located anywhere, in any module.  

```
@JAXBUtil.JAXBRegistration(target = "JAXBContextName")
@XmlRootElement(name = "file-root", namespace = "http://some-namespace.xsd")
public class SomeTypeExt extends SomeJAXBType {

}
```


To create a JAXBContext knowing the registered type, simply do: 

```
final Class[] clz = JAXBUtil.lookupJAXBTypes("JAXBContextName", SomeJAXBType.class); 
JAXBContext ctx;
try {
    ctx = JAXBContext.newInstance(clz);
} 
catch (JAXBException ex) {
    .....
}
```

No module dependency on the other module containing the registered type is required!
