# jwt_Token
jwt(token document)
 jwt:json web token -string 
1. website: https://jwt.io/
  
2. structure：
   1. header
   2. playLoad
   3. signature

3.常用的jwt(studen2 - day02_login)
    1.java-jwt 
     1. code - JavaJwtTest
    2.jj-jwt
     1. code - JjwtTest
     2. 如果出现：User
      java.lang.NoClassDefFoundError: javax/xml/bind/DatatypeConverter
      在 maven中加入 -javax.xml.bind:jaxb-api