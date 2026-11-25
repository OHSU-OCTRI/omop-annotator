# Upgrading

## 3.0.0

This version upgrades to Spring Boot 4 and Java 21. These runtime requirements should be synchronized between this project and relevant dependencies, such as the OCTRI Java libraries.

### Index

Many dependencies have been affected by the upgrade, including major version bumps to Hibernate Search. Because of these, upgraded systems should delete their existing indices and reindex through the admin UI once the application is running.

### Dialects

Specific dialect versions no longer exist in Hibernate 7, so any overridden properties or .env variables should be updated according to `RUNNING.md` before launching the application.
