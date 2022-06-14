# Running the OMOP Annotator Jar

## Prerequisites

The Annotator is a java application and requires the Java 11 Runtime Environment for running the provided jar.

The application requires two data sources - the read-only OMOP database and a writeable database to store application users and their annotations. Both data sources are configurable, but the writeable data source has only been tested with MySQL, and the OMOP data source has been tested with Oracle and Postgres.

## Configuration

The simplest way to set up data sources and other configuration for the application is to create a properties file to override expected properties defined in application.properties on the classpath. For example, create a file called override.properties in the same location as the jar. Once this is filled out with the required information, run the jar providing the override location:

```java -jar omop_annotator.jar --spring.config.location=classpath:/application.properties,file:///Users/home/Annotator/override.properties```

After the jar is running, the app can be found at the context path provided. For a local instance, this would be:

```
http://localhost:8080/omop_annotator/
```

### Properties for the Writeable Data Source

The following properties must be provided in the override file if they differ from the default values shown below:

```
app.datasource.url=jdbc:mysql://localhost:3306/omop_annotator?serverTimezone=America/Los_Angeles
app.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
app.datasource.username=omop_annotator
app.datasource.password=omop_annotator
app.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

### Properties for the OMOP Data Source

The OMOP Data Source has no default configuration, so these properties must be provided in the override file. Below are examples of Oracle and Postgres connections:

Oracle:
```
omop.datasource.url=jdbc:oracle:thin:@host:port:SID
omop.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
omop.datasource.username=your_username
omop.datasource.password=your_password
omop.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
```

Postgres:
```
omop.datasource.url=jdbc:postgresql://localhost:5432/mimic_3?currentSchema=omop
omop.datasource.driver-class-name=org.postgresql.Driver
omop.datasource.username=your_username
omop.datasource.password=your_password
omop.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect
```

### Display Properties

For most use cases of this application, it is expected that the OMOP data source will be static. This allows for annotation that is relevant to a snapshot in time and guaranteed to be unchanging and comparable to other unchanging data. To accommodate this, a refresh date must be provided in the properties file:

```
omop.data.refreshDate=2022-03-01
```

The participant age displayed in the application is calculated based on this date.

The format that dates are displayed in throughout the application is also configurable. Add this property to the override file if you need to change the default below:

```
omop.data.dateFormat=yyyy-MM-dd HH:mm:ss
```

### Authentication Properties

The application supports two methods of authentication, and both may be used. If your organization uses LDAP and all users are internal, you can enable LDAP authentication and provide the properties below:

```
octri.authentication.enable-ldap=true
ldap.context-source.url=
ldap.context-source.user-dn=
ldap.context-source.password=
ldap.context-source.search-base=
ldap.context-source.search-filter=
ldap.context-source.organization=
```

If you will have external users or do not have LDAP, you can enable table-based users:

```
octri.authentication.enable-table-based=true
```

## Spring Mail (Table-Based Authentication)

Spring Mail is used to communicate with table-based users when setting or resetting passwords. If the application will have table-based users, the settings should be configured for your organization. At a minimum, the 'from' address should be changed below:

```
spring.mail.enabled=false
spring.mail.from=octrihlp@ohsu.edu
spring.mail.default-encoding=UTF-8
spring.mail.host=smtpout.ohsu.edu
spring.mail.port=25
spring.mail.protocol=smtp
spring.mail.test-connection=false
spring.mail.username=octrihlp@ohsu.edu
spring.mail.password=secret
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false
spring.mail.properties.mail.smtp.starttls.required=false
```

This should be all the configuration needed. When you run the jar for the first time, the writeable database schema will be initialized and the database will be empty.

## Add a User

To log into the application, the first user needs to be created directly in the database and assigned a role of Admin. That user will then be able to log on and create other users in the database.

If your organization is using LDAP, you can amend the following SQL statements to create the first user:

```
INSERT INTO `omop_annotator`.`user` (`account_expired`, `account_locked`, `consecutive_login_failures`, `credentials_expired`, `email`, `enabled`, `first_name`, `institution`, `last_name`, `username`)
VALUES (0, 0, 0, 0, '<org_email>', 1, '<first_name>', '<institution_name>', '<last_name>', '<username>');

SET @userid = (SELECT last_insert_id());
SET @admin = (SELECT id FROM user_role WHERE role_name = 'ROLE_ADMIN' limit 1);
INSERT INTO user_user_role (user, user_role)
VALUES (@suserid, @admin);
````

If your organization will have table-based users only, start by creating the first user and role, setting the credentials to expired, and providing an email that will be used for confirmation of password reset:

```
INSERT INTO `omop_annotator`.`user` (`account_expired`, `account_locked`, `consecutive_login_failures`, `credentials_expired`, `email`, `enabled`, `first_name`, `institution`, `last_name`, `username`)
VALUES (0, 0, 0, 1, '<email>', 1, '<first_name>', '<institution_name>', '<last_name>', '<username>');

SET @userid = (SELECT last_insert_id());
SET @admin = (SELECT id FROM user_role WHERE role_name = 'ROLE_ADMIN' limit 1);
INSERT INTO user_user_role (user, user_role)
VALUES (@userid, @admin);
````

Now, create a password reset token for the user. The token can be any unique string up to 255 characters. This example uses '12345':

```
SET @expiration = (SELECT DATE_ADD(NOW(), INTERVAL 10 DAY));
INSERT INTO password_reset_token(token, expiry_date, user)
VALUES ('12345', @expiration, @userid);
```

With the jar running, navigate to the application's password reset token url, providing the token parameter:

```
http://localhost:8080/omop_annotator/user/password/reset?token=12345
```

You will be prompted to see a new password and will be able to log in once completed.