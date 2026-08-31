[English](https://github.com/Ekristoffe/WebVisu-Starter) / [日本語](README-JP.md)

# WebVisu Starter
 The WebVisu function for Ethernet controllers using CoDeSys 2.3 or WAGO-I/O-PRO CAA IDE enables real-time monitoring through a web browser. 
 Traditionally, this functionality relied on Oracle Java™ Applet technology.

 In 2017, Java™ was upgraded to Version 9, introducing significant security enhancements. 
 As part of this update, Java™ Applet support was removed. 
 Furthermore, support for Java™ Applets has been discontinued in modern web browsers. 
 As a result, Java™ Applets can no longer be launched, even when using versions of Java™ earlier than Version 9.

 Consequently, when accessing the WebVisu function stored on Ethernet controllers from an updated web browser or PC, the visualization may no longer be displayed.

 To address this issue, this simple Java™ application was created. 
 It allows existing WebVisu visualizations to continue operating, even though modern web browsers no longer support Java™ Applets.

 In addition, Oracle changed its Java™ licensing model in January 2019 (Version 8u202 and later). 
 Commercial use now requires a paid license, except when Java™ is used for development, testing, prototyping, or demonstration purposes.

 For this reason, we recommend using the Eclipse Temurin JRE (Java Runtime Environment).

 Eclipse Temurin is a trademark of the Eclipse Foundation. 
 It is a distribution of OpenJDK, the open-source implementation of the Java™ Platform, Standard Edition (Java™ SE). 
 While Java™ itself is a trademark of Oracle, Eclipse Temurin provides a free and open-source alternative for running Java™ applications.


Documentation
-------------
 <details>

 <summary>Install JAVA™</summary>

 ### Download OpenJDK (JRE)

 If Java™ is not installed on your PC, it can be downloaded from the Eclipse Adoptium website:

 <https://adoptium.net/temurin/releases?mode=filter>

 In the Release Filter, select the Version **8 - LTS**.  
 In the Operating system, select **Windows**.  
 In the list, Select **JRE** and the architecture (x64 or x32).  
 Then click on the download icon (Left of the **.MSI**) to download the JRE installer.  

 ### Install Java™ JRE
 Start the installer,  
 ![Temurin 1](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_EN_1.png?raw=true)  

 Accept the Licence Agreement, click [Next].  
 ![Temurin 2](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_EN_2.png?raw=true)  

 Select [Install for all users of this machine], click [Next]  
 ![Temurin 3](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_EN_3.png?raw=true)  

 Expand [JRE with Hotspot] and select [Entire feature will be installed on local hard drive], click [Next].  
 ![Temurin 4](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_EN_4.png?raw=true)  

 Click [Install] to begin the installation. Administrator privileges may be required.  
 ![Temurin 5](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_EN_5.png?raw=true)  

 When the installation is complete, click [Finish]  
 ![Temurin 6](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Temurin_EN_6.png?raw=true)

 </details>  

 ### Webvisu Starter

 Open the Releases page: <https://github.com/Ekristoffe/WebVisu-Starter/releases> from the right panel link.  
 Click on the latest Webvisu_Starter.jar to begin the download.  

 Once the download is complete, double-click Webvisu_Starter.jar.  

 The following screen will be displayed.  

 ![Webvisu_Starter](https://github.com/Ekristoffe/WebVisu-Starter/blob/main/images/Webvisu_Starter.png?raw=true)  

 Enter the controller's IP address in the "IP Address" field.  
 If you check "Ping Req", a PING command will be sent before WebVisu starts.  
 Click the "Start" button to launch the WebVisu function.  

<br>

Disclaimer
-------------

<div style="background-color: lightgray;">
<span style="color: red">
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT, OR OTHERWISE, ARISING FROM, OUT OF, OR IN CONNECTION WITH THE SOFTWARE OR THE USE OF THE SOFTWARE OR OTHER DEALINGS IN THE SOFTWARE.
</span>
</div>
