this note if for recording how to do in eclipse and maven

Using eclipse and maven

please install eclipse and maven

### maven project 
maven-archetype-quickstart

GroupID：idv.jingshing. fore means: com as company name, org means non profittable and idv means individual. jingshing is my name.

ArtifactID：project name

### Opencv

1. In the Run menu, select Run Configuration.
2. Go to the Arguments tab
3. Add this in the VM arguments field:```-Djava.library.path="/path/to/OpenCV/library"``` // this path is to the folder put dll

### pom.xml
```
<dependency>
		<groupId>org.opencv</groupId>
   		<artifactId>opencv</artifactId>
   		<version>460</version>
</dependency>
```

### DLL
remeber to change Native library location to the folder put dll file.
