# URLshortener 
###Software Installation
####Prerequisites
1. Java should be installed on the system
  a. Install the JDK software
  b. Select the appropriate JDK software and click Download.
  c. The JDK software is installed on your computer, for example, at C:\Program Files\Java\<jdk_version>. You can move the JDK software to another location if desired.
  d. Set JAVA_HOME:
    Right click My Computer and select Properties.
    On the Advanced tab, select Environment Variables, and then edit JAVA_HOME to point to where the JDK software is located, for example, C:\Program Files\Java\<jdk_version>
  e. Set JRE_HOME
     Right click My Computer and select Properties.
    On the Advanced tab, select Environment Variables, and then edit JRE_HOME to point to where the JDK software is located, for example, C:\Program Files\Java\<jdk_version>\jre\
2. Eclipse - Java EE neon should be installed.
3. Tomcat 8.0.35 should be installed and configured as per mentioned in Chapter 2.
4. Maven should be installed and configured on the system.
5. Ant should be installed and configured on the system.
  
###Installation and Running Application
####Installing and Configuring Database
1. Download and install Apache Ant.
2. Use Ant to install and run HSQLDB.
3. For running HSQLDB:
  a. Unzip the Submitted ZIP file
  b. You will find a file named ‘build.xml’ and URLShortner.zip
  c. Copy ‘build.xml’ to a new empty directory.
  d. Open ‘cmd’ in the new folder where you have placed ‘build.xml’
  e. Type following commands:
    i. ant -projecthelp (This will open up all the options for HSQLDB)
    ii. ant clean
    iii. ant start
    iv. ant createTables (This will create relevant tables and populate Users table with a default value. Username: testUser and Password: testPassword)

###Running Through WAR
####Installation - Creating WAR in Eclipse IDE
1. Unzip the URLShortner.zip
2. Open eclipse
3. Click on File -> Import -> Maven -> Existing Maven Projects -> Browser to the location where the project was extracted
4. Click Finish
5. Right click on Project from the eclipse Project Explorer. -> Run As -> Maven Build
6. Define goals: ‘clean install’
7. Click Run
8. Wait for the installation to complete. Till we get a build success message in console. This also indicates that a WAR file for the project has been created in the target folder of the project.

####Installation - Creating WAR using mvn package command
1. Unzip the project
2. Open cmd in the Project folder
3. Type command ‘mvn package’
4. Wait for the installation to complete. Till we get a build success message in console. This also indicates that a WAR file for the project has been created in the target folder of the project.

####Running Application using Created WAR
1. Copy the created WAR (URLShortner.war) from target folder into the webapps directory of Tomcat.
2. Start tomcat
3. Access the application at http://localhost:8080/URLShortner/

####Running through Eclipse
1. Unzip the project
2. Open eclipse
3. Click on File -> Import -> Maven -> Existing Maven Projects -> Browser to the location where the project was extracted
4. Click Finish
5. Right click on Project from the eclipse Project Explorer. -> Run As -> Run On Server
6. Check Console to see if the server has started
7. Application will start at http://localhost:8080/URLShortner/
