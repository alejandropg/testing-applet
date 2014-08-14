# Testing Applet

En este ejemplo vamos a explorar como testear de forma automática un applet.

Haremos tanto test untirarios que prueban de forma local el applet como tests de sistema donde ejecutaremos los tests dentro de un navegador con todas las restricciones de seguridad que ello implica.



## Requisitos

*   Java >= v1.7
*   Gradle >= 1.11
*   Node >= v0.10.26
*   OpenSSL >= 0.9.8y



## 1. Los certificados

Para generer los certificados necesitamos tener instalado OpenSSL.

Estando en la raiz del proyecto ejectamos el script `generate-demo-certs.sh`. Esto nos creará el directorio `certs/`.

Importamos el certificado de la CA en el cacerts del navegador. Esto es muy dependiente del sistema, y por eso lo hacemos a mano. Hay un ejemplo comentado en el propio script.

    BROWSER_CACERTS="/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/lib/security/cacerts"
    sudo keytool -keystore "$BROWSER_CACERTS" -storepass changeit -importcert -alias demoCA -file ./certs/cacert.pem -noprompt



## 2. El servidor

El servidor funciona con __node__, y se encarga de servir de forma estática los ficheros que hay en `src/main/webapp` en el proyecto `demo/system-test`.

Es necesario tener instalada la librería __connect__. Para ello podemos hacer:

    cd demo/system-test
    $npm install connect

Para levantar el servidor:

    $node server.js

Ahora se puede acceder a la página de prueba mediante la URL http://localhost:8080/index.html



## 3. Compilar el proyecto

Basta con ir a la raiz del proyecto y ejecutar:

    gradle clean build

Esto no sólo compilará todas las clases, sino que lanzará todos los tests, incluidos los de sistema, que levantan
un Firefox y hacen peticiones contra el servidor de Node que hemos levantado en el paso anterior.



## 4. Tutorial

He hecho un tutorial para intentar explicar mejor el código y que sirva de guía para todos los módulos que se encuentran en este repositior.

El tutorial está publicado en: <http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=testing-applet>



## 5. Documentación de referencia y ejemplos

* <http://docs.oracle.com/javase/tutorial/deployment/applet/index.html>
* <http://docs.oracle.com/javase/tutorial/deployment/applet/examplesIndex.html>
* <http://docs.oracle.com/javase/7/docs/technotes/guides/jweb/security/manifest.html>
* <http://docs.oracle.com/javase/8/docs/technotes/guides/jweb/security/manifest.html>
* <http://www.oracle.com/technetwork/java/javase/7u-relnotes-515228.html>
* <https://jdk6.java.net/plugin2/liveconnect/>
* <https://blogs.oracle.com/java-platform-group/entry/liveconnect_changes_in_7u45> (October 2013)
* <https://blogs.oracle.com/java-platform-group/entry/new_security_requirements_for_rias> (in 7u51 (January 2014))
* <http://java.com/en/download/help/appsecuritydialogs.xml#revokecheck>

