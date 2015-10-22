tomcat ssl

keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA1withRSA -validity 36500 -alias cas.server.com -keystore d:/tomcat.keystore -dname "CN=cas.server.com,OU=cas,O=cas,L=shanghai,ST=shanghai,C=CN"
keytool -exportcert -alias cas.server.com -keystore d:/tomcat.keystore  -file d:/tomcat.cer -rfc
keytool -import -alias cacerts -keystore cacerts -file d:/tomcat.cer -trustcacerts

cas 
http://blog.csdn.net/zhurhyme/article/category/2362091
http://sgq0085.iteye.com/category/302777

server.xml

	<Connector port="8443" protocol="org.apache.coyote.http11.Http11Protocol"
              maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
              clientAuth="false" sslProtocol="TLS" 
              keystoreFile="conf/localhost.keystore"  
              keystorePass="123456" />


web.xml (http => https)

 	<security-constraint> 
       <web-resource-collection > 
              <web-resource-name >SSL</web-resource-name> 
              <url-pattern>/*</url-pattern> 
       </web-resource-collection>
                             
       <user-data-constraint> 
              <transport-guarantee>CONFIDENTIAL</transport-guarantee> 
       </user-data-constraint> 
    </security-constraint>
    
    
    
      CAS 服务器端取消 https的配置 方法

 

需要修改的配置文件有：

WEB-INF/deployerConfigContext.xml 、 WEB-INF/spring-configuration/ticketGrantingTicketCookieGenerator.xml 、

WEB-INF\spring-configuration\warnCookieGenerator.xml

 

详细配置修改如下：

1 、 WEB-INF/deployerConfigContext.xml

    在

    < bean class = "org.jasig.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler"     p:httpClient-ref = "httpClient" />

增加参数 p:requireSecure="false" ，是否需要安全验证，即 HTTPS ， false 为不采用 如下：

< bean class = "org.jasig.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler" p:httpClient-ref = "httpClient" p:requireSecure= "false" />

      

2 、 WEB-INF/spring-configuration/ticketGrantingTicketCookieGenerator.xml
         修改 p:cookieSecure="true" 为 p:cookieSecure=" false " ， 即不需要安全 cookie

如下部分：

    < bean id = "ticketGrantingTicketCookieGenerator" class = "org.jasig.cas.web.support.CookieRetrievingCookieGenerator"

       p:cookieSecure = " false "

       p:cookieMaxAge = "-1"

       p:cookieName = "CASTGC"

       p:cookiePath = "/cas" />

 

3 、 WEB-INF\spring-configuration\warnCookieGenerator.xml

修改 p:cookieSecure="true" 为 p:cookieSecure=" false " ， 即不需要安全 cookie

结果如下：

    < bean id = "warnCookieGenerator" class = "org.jasig.cas.web.support.CookieRetrievingCookieGenerator"

       p:cookieSecure = " false "

       p:cookieMaxAge = "-1"

       p:cookieName = "CASPRIVACY"

       p:cookiePath = "/cas" /> 