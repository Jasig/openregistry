##Open Registry


#What is OpenRegistry?
OpenRegistry is an OpenSource Identity Management System (IDMS). It's a place for data about people 
affiliated with your organization.
#Core Functionality
Interfaces for web, batch, and real-time data transfer
Identity data store
Identity reconciliation from multiple systems of record
Identifier assignment for new, unique individuals
#Why OpenRegistry?
"Off the shelf" solutions usually end up requiring significant customizations and integration work and/or solve only a portion of an institution's needs
Lots of institutions still rolling their own
Combined institutional efforts better leverage scant resources and allow for learning from others' experience (eg: Sakai, uPortal, CAS, Shibboleth, Kuali)
OpenRegistry is tailored to the needs of higher education
#Building OpenRegistry using Maven2
Building OpenRegistry via the Maven2 system is simple. From within the PROJECT_HOME directory, execute the following command:

mvn clean package install

#Generating the DDL for YOUR database

mvn hibernate3:hbm2ddl

#For Mor Information
https://wiki.jasig.org/display/ORUM/Home
