English | [繁體中文](README_TCH.md)
# Pixel-Art-Filter-Java
Pixel Art filter made with Java

This project is using [Python Pixel Art Filter Tool](https://github.com/JingShing-Tools/Pixel-Art-transform-in-python) as reference. And use Java to rewrite the project.

Still working on...

## Envirorment
I don't use any of IDE or project set. So I will give a method of how to use opencv with javac in cmd and local envirorment.

Before we started. It will need to go to [Opencv Files](https://sourceforge.net/projects/opencvlibrary/files/) download the newest. You will get  ```.exe``` compressed file. Activate it to get a file.

After depressed it. You will get a folder named ```opencv``` . Go to this file path ```opencv\opencv\build\java``` can get a compiled opencv library called ```opencv-version.jar``` . Put this file in the same folder of ```PixelTransform.java```. And in ```x64``` and ```x86``` folder had ```.dll``` file. Please choose one which can run in your os in the same folder of ```PixelTransform.java```.

Activate CMD and using CD load to the folder of ```PixelTransform.java```. Enter this command to compile the file:  ```javac -d . -classpath .;opencv-version.jar PixelTransform.java```

And if you want to run the compiled class please enter this command: ```java -classpath .;opencv-version.jar pixel.filter.PixelTransform```

## Version
### Ver 0.1
* Successfully rewrite Python code to Java
* Feature:
  * Pixel Size 
  * Color number
### Ver 0.2
* Feature:
  * Custom input
  * Outline(erode)
  * Blur(bilateralFilter)
