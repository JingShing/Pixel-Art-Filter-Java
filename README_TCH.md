[English](README.md) | 繁體中文
# 像素濾鏡工具 Java 版
透過 Java 製作像素濾鏡工具。

本專案透過 [Python 像素濾鏡工具](https://github.com/JingShing-Tools/Pixel-Art-transform-in-python) 作為參考專案。以此延伸用 Java 編寫此專案。

仍在開發...

## 環境
我沒有使用任何 IDE 和 Java 專案管理工具。底下會說明如何使用 javac 和 cmd 在本地環境運行代碼。

在開始前，建議到 [Opencv Files](https://sourceforge.net/projects/opencvlibrary/files/) 選擇喜歡的版本下載。下載後會得到 ```.exe``` 的壓縮檔。啟動後解壓縮。

解壓縮後會得到一個名稱為 ```opencv``` 的資料夾。開啟以下路徑 ```opencv\opencv\build\java``` 可以得到一個已經編譯好的 ```opencv-版本號.jar``` 的 opencv 庫。將此放入到放置本專案 ```PixelTransform.java``` 的同層資料夾中。除此之外還有兩個 ```x64``` 和 ```x86``` 的資料夾。裡面裝有執行時所需的 ```.dll``` 檔。請依據系統放置其一到放置 ```PixelTransform.java``` 的資料夾中。

將 Opencv 的部分弄完後。

開啟 CMD 並透過 CD 指令讀取到放置 ```PixelTransform.java``` 的資料夾。輸入 ```javac -d . -classpath .;opencv-版本號.jar PixelTransform.java``` 即可編譯好 ```.class``` 檔。

執行則需輸入 ```java -classpath .;opencv-版本號.jar pixel.filter.PixelTransform```

## 版本
### Ver 0.1
* 成功透過 Python 代碼重構成 Java 的 Opencv 調用。
* 功能實現:
  * 顆粒大小  
  * 顏色數量