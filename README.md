dtoMapper
=========
[![Build Status](https://travis-ci.org/pokaru/dtoMapper.png?branch=master)](https://travis-ci.org/pokaru/dtoMapper)

I'm sure there may be other tools out there, but this is my quick stab at a simple framework to automate the process of mapping dto objects to other objects **_(and vice-versa)_**.

With dtoMapper, you can transer a single DTO's field values **_to and from multiple_** objects, without calling a single getter or setter.

Usage
-----
```java
...

ObjectMap objectMap = new ObjectMap();
objectMap.put("someKey", object1);
objectMap.put("someOtherKey", object2);

//move data from the dto to objects in the map
mapper.fromDto(dto, objectMap);

//move data from objects in the map to the dto
mapper.toDto(dto, objectMap);
```

Annotations
-----------
Mapping configurations are purely annotation-based.  All the configuration annotations associated with this tool are described below.

**@MappedObject(key="someKey")** (class-level)
> When you annotate your DTO with this annotation, you set which object your DTO will map to.  The **key** attribute is required and used to reference which object in a map (discussed later) your DTO will map to.
> All DTO fields, by default, will map to fields in the mapped object, referenced by the **key**.
> The mapper will assume the field names in the DTO are identical to the field names in the mapped object.

**@MappedField** (field-level)
> #### Attribute: field="someFieldName"
> In the case where field names in the mapped object aren't the same as the field names in the DTO, you can annotate a DTO field with this annotation.  Using the **field** attribute, you can specify the field name in the mapped object that the annotated DTO field will map to.

> #### Attribute: mappedOjbectKey="someKey"
> Setting this attribute is equivalent to setting the @MappedObject annotation, but at the field-level.  Using this attribute, you can map a DTO field to an object other than the one referenced by the @MappedObject class-level annotation.
> If each field in the in the DTO is annotated with this annotation and **mappedObjectKey** is set, the @MappedObject class-level annotation isn't necessary or required.

> #### Attribute: mapsTo= (MapsTo.FIELD | MapsTo.SETTER)
> You can tell the mapper to map a DTO field to a setter method of the mapped object by setting this attribute to MapsTo.SETTER.  By default this attribute is set to MapsTo.FIELD, which maps DTO fields to the mapped object fields.  The setter method name will be constructed by the mapper from the field name (ie someField would turn into setSomeField).

**@Ignore** (field-level)
> If you ever need to declare a field in a DTO that shouldn't be mapped, annotating that field with this annotation will have the mapper ignore that field during the mapping process.

**@Embedded** (field-level)
> dtoMapper supports embedded dto mapping.  DTO fields decorated with this annotation will be inspected by the Mapper and their field values will be transfered to objects found in the object map.

**@Convert** (field-level)
> #### Attribute: converter=SomeConverter.class
> dtoMapper also supports type conversions.  To convert the type of a field, during the mapping process, annotate the field to convert with this annotation and specify one of the built in conversion classes in the converter attribute.  You can also extend this framework by writing your own converstion classes.  To write your own conversion class, you simply need to have your conversion class extend the abstract Converter class.

**@Rules** (class-level)
> #### Attribute: value={Rule1.class, Rule2.class, Rule3.class}
> When your dto mappings become too complex for the annotations above to handle, this annotation provides a way for a developer create custom mapping rules.  This includes mapping a single dto field to multiple fields in the same object or different objects, conditional mappings, etc.  Using this annotation in combination with custom rules, allows for virtually limitless mapping possibilities.  To create a custom rule, have your rule class extend the abstract Rule class.

If none of this makes sense, take a look at the examples below.

Object Map
----------
The `String` to `Object` map, that you'll pass to the mapper is the object look-up.  The keys you specify in this map are the same keys that will be referenced by the annotations described above.

DTO's, for the most part, are flat in structure.  Business objects won't always be as flat as the DTO's that map to them.  To set values within a mapped object's nested object, put the nested object into the `objectMap` and use the annotations above to map DTO fields to that nested object's fields.

Examples
--------
**Class-Level Mapping (Simple Case)**
```java
@MappedObject(key="someKey")
public class MyDTO{
    private String someField;
}
```
**Class-Level Mapping (w/a DTO field mapped to a field by a differnt name)**
```java
@MappedObject(key="someKey")
public class MyDTO{
    @MappedField(field="anotherName")
    private String someField;
}
```
**Class-Level Mapping (w/a field mapped to a different object.)**
```java
@MappedObject(key="someKey")
public class MyDTO{
    private String someField;
    
    @MappedField(mappedObjectKey="someOtherKey")
    private String someField2;
}
```
**Class-Level Mapping (w/a field mapped to a field with a different name within a different object)**
```java
@MappedObject(key="someKey")
public class MyDTO{
    private String someField;
    
    @MappedField(mappedObjectKey="someOtherKey", field="anotherName")
    private String someField2;
}
```
**Mapping to a Setter Method**
```java
@MappedObject(key="someKey")
public class MyDTO{
    private String someField;
    
    @MappedField(mapsTo=MapsTo.SETTER)
    private String someField2;
}
```
**All Field-Level Mappings**
```java
public class MyDTO{
    @MappedField(mappedObjectKey="someKey")
    private String someField;
    
    @MappedField(mappedObjectKey="someOtherKey", field="anotherName")
    private String someField2;
}
```
**Embedded DTOs**
```java
public class MyDTO{
    @Embedded
    private SomeGroupedDataDTO someGroupedData;
    @Embedded
    private SomeMoreGroupedDataDTO someMoreGroupedData;
}
```
**Type Conversion**
```java
public class MyDTO{
    @Convert(converter=StringToIntegerConverter.class)
    @MappedField(mappedObjectKey="someKey")
    private String someField;
}
```
**Ignored Field**
```java
@MappedObject(key="someKey")
public class MyDTO{
    @Ignore
    private String someField;
}
```
**Custom Rules**
```java
@Rules({SomeRule.class})
@MappedObject(key="someKey")
public class MyDTO{
    private String someField;
    private String someOtherField;
}
```

A detailed explanation on how to create custom rules and type conversions is on the way.
