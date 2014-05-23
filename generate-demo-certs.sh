#!/bin/bash
# Script para generar los certificados para firmar el applet.
#
# Documentación de referencia:
#    http://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html
#    https://www.openssl.org/docs/apps/openssl.html
#

BASE=`pwd`
CERTS_DIR="./certs"
KEYSTORE="testing-applet.jks"
STOREPASS="autentia"
VALDAYS="3650"

rm -Rf $CERTS_DIR
mkdir -p $CERTS_DIR
cd $CERTS_DIR

# > Por la CA.
echo "[DEBUG] Preparamos una CA 'fake' para la demo."
mkdir -p ./demoCA/private
mkdir -p ./demoCA/newcerts
touch ./demoCA/index.txt
echo 01 > ./demoCA/serial

openssl genrsa -out ./demoCA/private/cakey.pem 2048
openssl req -new -x509 -days $VALDAYS -key ./demoCA/private/cakey.pem -out ./demoCA/cacert.pem -config $BASE/src/main/config/demoCA.config

# < Por nosotros.
echo "[DEBUG] Importamos el certificado con la clave púlica de la CA de demo en nuestro kaychain."
echo "[DEBUG] Ojo!!! Esto habrá que hacerlo a mano en el cacerts de la JRE de nuestro navegador, para que luego el applet funcione correctamente!"
keytool -keystore $KEYSTORE -storepass $STOREPASS -importcert -alias demoCA -file ./demoCA/cacert.pem -noprompt

# Importamos el certificado de la CA en el cacerts del navegador.
# Está comentado porque es muy dependiente del entorno, así que hay que hacerlo a mano o editar este script para activarlo.
#BROWSER_CACERTS="/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/lib/security/cacerts"
#sudo keytool -keystore "$BROWSER_CACERTS" -storepass changeit -delete -alias demoCA
#sudo keytool -keystore "$BROWSER_CACERTS" -storepass changeit -importcert -alias demoCA -file ./demoCA/cacert.pem -noprompt

echo "[DEBUG] Genermaos los certificados autofirmados para luego crear la cadena de certificaods."
DNAME="C=ES, ST=Madrid, L=SanFernandoDeHenares, O=Autentia, OU=Tutoriales, CN=Testing Applet Cert"
DNAME1="$DNAME"1
DNAME2="$DNAME"2
keytool -keystore $KEYSTORE -storepass $STOREPASS -keypass $STOREPASS -alias demoCert1 -dname "$DNAME1" -genkeypair -ext bc=ca:true
keytool -keystore $KEYSTORE -storepass $STOREPASS -keypass $STOREPASS -alias demoCert2 -dname "$DNAME2" -genkeypair

echo "[DEBUG] Generamos una petición de certificaod que 'enviaremos' a nuestra CA de demo."
keytool -keystore $KEYSTORE -storepass $STOREPASS -alias demoCert1 -certreq -file demoCert1.csr

# > Por la CA.
echo "[DEBUG] El certificado es firmado por la CA de demo."
openssl ca -in demoCert1.csr -out demoCert1.pem.cer -config $BASE/src/main/config/demoCA.config -batch

# < Por nosotros.
echo "[DEBUG] Importamos en nuestro keystore el certificado que nos ha creado la CA de demo, convenientemente firmado por ella."
keytool -keystore $KEYSTORE -storepass $STOREPASS -alias demoCert1 -importcert -file demoCert1.pem.cer

echo "[DEBUG] Genermos la cadena entre demoCert1 y demoCert2, de forma que demoCert1 firme a demoCert2."
echo "[DEBUG] Así la cadena queda demoCA --> demoCert1 --> demoCert2"
keytool -keystore $KEYSTORE -storepass $STOREPASS -alias demoCert2 -certreq \
    | keytool -keystore $KEYSTORE -storepass $STOREPASS -alias demoCert1 -gencert -validity $VALDAYS \
    | keytool -keystore $KEYSTORE -storepass $STOREPASS -alias demoCert2  -importcert

echo ""
echo "Par ver el resultado puede hacer: $ keytool -keystore $KEYSTORE -storepass $STOREPASS -list -v"
echo ""
