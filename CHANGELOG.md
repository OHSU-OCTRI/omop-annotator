# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [unreleased]

### Changed

- Add CHANGELOG.md
- Update URLs after move to GitHub Enterprise Cloud (CRIOPS-248)
- Use GitHub Actions for continuous integration (CRIOPS-248)
- Upgrade to AuthLib 2.2.1 (CRIOPS-248)

### Fixed

- Fix filebeat tokenizer pattern

## [2.0.2] - 2025-04-02

### Changed

- Override Tomcat version to address Tomcat CVE (CIS-3116)

## [2.0.1] - 2024-11-08

No changes.

## [2.0.0] - 2024-11-08

Upgrade to Spring Boot 3.

### Changed

- Update to Spring 2.7.18 (OA-180)
- MySQL container bug (OA-180)
- Bump Java version (OA-180)
- Authlib 0.10 upgrade (OA-180)
- Java 17 upgrade (OA-180)
- Dependency problems (OA-180)
- Working on build errors (OA-180)
- Attempt type fix (OA-180)
- ORM Mapper to Jakarta (OA-180)
- Upgrades for enums and search. (OA-180)
- Postgres support - Oracle broken. (OA-180)
- Package level registration (OA-180)
- Fix Note text and improve Float conversion (OA-180)
- Runtime Type Configuration for PostgreSQL (OA-180)
- Spring Upgrade (OA-180)
- Spell Check SecurityConfiguration.java
- Upgrade tidying (OA-180)
- Merge remote commit (OA-180)
- Typo in OmopTypeContributor.java (OA-180)
- Update Log Dissector for Spring Boot 3 (OA-186)
- Use Hibernate search for notes (OA-187)
- Add config for default schema (OA-191)
- Upgrade authlib 1.1.0 (OA-190)
- Upgrade authlib 1.3.0 (OA-190)
- Upgrade authlib 2.1.1 (OA-190)
- Node upgrade (OA-190)

### Fixed

- Fix Topic Bulk Upload URL (OA-188)
- Fix Bulk Upload of Pool Entries (OA-188)
- Fix Hibernate search query for note text (OA-187)

### Removed

- Removed docker compose platform
- Remove TODO
- Remove Debug Logging (OA-180)

## [1.4.0] - 2023-05-04

### Added

- Add UI/logic for merging pools. (OA-172)
- Restrict merge if topics overlap.

### Fixed

- Fix display configuration editing (OA-174)
- Fix reference to old field name (OA-174)

## [1.3.2] - 2023-04-24

### Added

- Add pin info to export. (OA-167)
- UI changes for selecting the visit filter
- Enable boolean searching in full text search (OA-173)

### Changed

- Run npm audit fix
- Initial implementation for hiding visits without data (OA-154)
- Merged latest from main
- Refactored OmopBrowser component to provide a control to toggle between visit
  filtering functions.
- Update judgment export help text.

## [1.3.1] - 2023-04-06

### Added

- Add modal for managing pins. (OA-168)
- Comment length validation.

### Fixed

- Fix tests.

## [1.3.0] - 2023-03-24

### Added

- Added unit tests
- Administrative UI for initiating and tracking Search Indexing Jobs. (OA-153)
- Pin visit proof of concept. (OA-142)
- Added patient_id_count field to SearchIndexJob
- Add enumeration for Pin entity field.
- Add interaction for showing only pinned visits.
- Admin Dashboard Pool Selection (OA-161)
- Export dashboard data (OA-162)

### Changed

- Increase Staging Resources (OA-155)
- Refactor to run MassIndexer in batches
- Update Maven (OA-159)
- Restructured indexing to run in batches (OA-163)
- Merge branch 'main' into pin-data
- Improve documentation.
- Forbid pin deletion by other users.
- Refactor all chatter into an Annotator Api
- Correct case sensitive import.
- Use OmopEntity enum for display config.
- Convert pinned visit list to set
- Make Pool label unique. (OA-161)

### Fixed

- Fix Drug display configuration. (OA-160)
- Fix tests.
- Fix search returning visit ids.

### Removed

- Remove user selection from judgment export. (OA-159)

## [1.2.0] - 2023-03-09

### Added

- Basic display config for visits. (OA-151)
- Additional visit field mappings.
- Add a dashboard for admins (OA-148)

### Changed

- Fix tests.
- Address PR #125 comments
- Refactor api calls into a few controllers.

### Fixed

- Correct the GetMapping for the AdminDashboard.
- Try addressing 404 on dev.

## [1.1.0] - 2023-02-24

### Added

- Added timeline visualization for visits
- Added tooltips; general cleanup
- Stubbed out click handling
- Added an isoDate field to the VisitOccurrenceRow to provide a consistent format
  for front end code; used the new field in the VisitTimeline component (OA-144)
- Search all person data using a single sql query
- Configure Condition List in UI (OA-146)
- Configuration for Observations, Procedures, Notes
- Add structure and config for Grouped Lists.
- Implements full text search using Hibernate Search
- Added documentation
- Add Volume for Search Data (OA-152)

### Changed

- Refactor to convert most d3 code to native Vuejs functionality, including
  tooltips with transitions
- Renamed component to VisitTimeline; added unit tests
- Streamlined imports
- Renamed VisitOccurrenceRow getIsoDate method to reflect that it comes from the
  visitStart field
- Switch to RestController and whitespace cleanup.
- Move fetches to OmopBrowser so list components are presentational only.
- Comment out tests until component works.
- Updated docker files to include search indices volume

### Fixed

- Fix warnings
- Try to fix bug related to async configuration.
- Fix OmopBrowser component tests.

### Removed

- Removed manual search configuration in favor of the annotation-based approach.

## [1.0.4] - 2022-12-15

### Added

- Sample histogram with random data
- Additional entity filtering. (OA-124)
- Use configuration to load NoteList (OA-124)
- Finalize filters and configuration of lists.

### Changed

- Default Measurement sort to name
- Replace var with let/const.
- Add no-var to ESLint Config
- Automatic JS Vulnerability Fixes

## [1.0.3] - 2022-08-31

### Changed

- Bump Dependencies (OA-129)

## [1.0.2] - 2022-08-23

### Added

- Add stages for public Github release (OA-122)

### Changed

- Resolve Vulerability in terser (OA-131)
- Release version 1.0.0-test
- Add description parameter to job.
- Release zip to Github repo (OA-122)
- Add release description.
- Release version 1.0.1
- Release version 1.0.1-test2
- Release to public Github (OA-122)
- Update jquery deps based on feedback from CyOps (OA-131)
- Work Around Timing Issue
- Specify UTF-8 on upload. (OA-94)
- Add static declarations of file format indices.

### Fixed

- Fix pom version.
- Fix upload for Windows-style returns. (OA-94)
- Fix blank line check. (OA-94)

## [1.0.0] - 2022-08-12

### Added

- Add warning to new pool creation (OA-85)
- Add visit source value (OA-113)
- Adding k8s manifest for prod to branch (OA-126)
- Export judgments (OA-119)
- Submit visit search on enter (OA-130)

### Changed

- Changed memory parameters for deployment k8s manifest in prod (OA-126)
- Add MS SQL Server JDBC Driver (OA-118)
- Custom Hibernate Naming Strategy (OA-118)
- Update Default Physical Naming Strategy (OA-118)
- Document SQL Server Connection Properties (OA-118)

### Fixed

- Fixed template ids

### Removed

- Remove generated Judgment UI and controller (OA-85)

## [0.7.2] - 2022-07-28

### Changed

- Update Dependencies (OA-126)
- Update AuthLib Config (OA-126)
- Update VS Code Config (OA-126)

## [0.7.1] - 2022-07-14

### Fixed

- Fixed bug with searching (OA-123)
- Fix issue when searching visit data followed immediately by a sub-entity search
  with the same term (OA-123)

## [0.7.0] - 2022-06-16

### Added

- Judgment UI Refinements (OA-121)
- Setup documentation for external groups. (OA-91)
- Add docs on db setup for table-based scenario.

### Changed

- Update RUNNING.md
- Update RUNNING.md
- Merge upstream branch 'setup-documentation' into setup-documentation

## [0.6.0] - 2022-06-02

### Added

- Add the medications view (OA-116)
- Add search filter for medications.
- OMOP Service Layer (OA-52)
- Auditing Machinery (OA-52)
- Judgment interface refinements; added filtering and scrolling for pool entries;
  fixed reactive UI issues (OA-114)

### Changed

- Configure Webpack CSS Extraction (OA-108)
- Use template strings for readability.
- Move drug grouping to omop-api for accurate counts.
- Refactor Service Package (OA-52)
- Rename List Methods (OA-52)
- Audit Persistence Test (OA-52)
- Merge branch 'main' into oa-52

### Fixed

- Fix Vue component test.
- Fixed a regression in visit search
- Fixed whitespace issues
- Fixed issue with note query

## [0.5.0] - 2022-05-19

### Added

- Add test and make general test improvements.
- Add indicator for returning to preview.
- Add UserType for Float conversions (OA-111)
- Add custom type for processing Java Float (OA-111)
- Added functionality to the OMOP browser to find visits by data contained in
  child entities (OA-92)
- Implement unwrap in custom java descriptor.

### Changed

- Make all ids integers for PostgreS compatibility. (OA-111)
- Documentation improvements
- Merge branch 'postgres-values' into hibernate-user-type
- Handle Oracle/Postgres Note Text (OA-111)
- Handle Oracle/Postgres Float (OA-111)
- Streamline the custom type implementation (OA-111)
- Updated to use the latest data types
- Renamed VS Code Extension
- Refactored loading spinner usage by visit list

### Removed

- Remove details row in favor if in-cell expansion
- Remove float columns for Postgres compatibility (OA-111)

## [0.4.0] - 2022-05-09

### Added

- Add domain and repo for OMOP Note (OA-74)
- Add view for Patient Notes. (OA-71)
- Configurable Main Classes (OA-102)
- Full-Width Layout (OA-102)
- Pool Entry Selection (OA-102)
- Add Judgment Buttons (OA-102)
- Added new component for previewing judgment buttons; added color utilities (OA-104)
- Added judge component preview to annotation schema detail page
- Added visit list control to view the context of a selected visit (OA-105)

### Changed

- Update tests.
- Prevent pool editing once pool entries exist.
- Format JavaScript (OA-102)
- Inject Ajax Request Dependencies (OA-102)
- Update UI On Judgment (OA-102)
- Update notes view now that data exists.
- Merged changes from upstream
- Moved judge button colors to stylesheet
- UI changes for better wrapping at different screen sizes; consistent navigation
  and instructions (OA-102)
- Lock pool entries from editing once judgments exist.
- Updated the VisitList component to hold the state of the search performed so we
  can use that reactively.
- Convert visit list contextual information to computed properties (OA-105)
- Improve notes view (OA-115)
- Alternate implementation with inline click/expand.

### Fixed

- ESLint Warning: Mutation in Computed Property
- Visit sorting bug fix (OA-105)

### Removed

- Remove unused import
- Fix copy/paste error.

## [0.3.0] - 2022-04-21

### Added

- Add Pull Request Template (CIS-1793)
- Additional Visit Details (OA-98)
- Observation Component (OA-69)
- Add Procedures Component (OA-68)
- Measurement List Component (OA-99)
- PersonDataBrowser Component (OA-100)
- Tabbed Interface (OA-100)
- OMOP API Wrapper (OA-100)
- Visit Selection (OA-100)
- Load Data in OmopBrowser (OA-100)
- Component to judge a pool entry (OA-72)
- Add VS Code Settings (CIS-1799)

### Changed

- Refactoring to optimize querying for condition occurrences without using native
  queries. (OA-95)
- Update minimist (OA-100)
- Rename `PersonDataBrowser` -> `OmopBrowser` (OA-100)
- Refactor data queries to use database-agnostic query strings (OA-95)
- More Compact Tables (OA-100)
- Use Prop for PersonSummary Data (OA-100)
- Mock Response Helper (OA-100)
- Extract Example Data for Tests (OA-100)
- Restore Number.parseInt Call (OA-100)
- Added missing file
- Update src/main/resources/frontend/components/JudgeEntry.vue
- Java settings for vscode (OA-1792)
- Add Section on Extensions to README (CIS-1799)
- Improve Instructions for Running (CIS-1799)
- UI styling for the patient detail page for readability
- Conditionally render id in visit details
- Temporary Webpack Build Workaround (OA-108)

### Fixed

- Fix ESLint Issues (OA-100)
- Fix Comment Text (OA-100)

### Removed

- Remove unnecessary date formatting on Procedures/Observations.
- Delete Obsolete JS Files (OA-100)
- Remove Fetch From List Components (OA-100)

## [0.2.0] - 2022-04-07

### Added

- Initial domain objects
- Added documentation for domain entities
- Controllers, Repositories and templates for initial data model
- Add Oracle JDBC Driver (OA-55)
- OMOP Data Source Configuration (OA-55)
- OMOP Person (OA-56)
- Additional OMOP domain entities.
- Add webpack config and integration with Maven (OA-58)
- Tests for AnnotationLabels.vue.
- Front end validation for display label on annotations.
- Bulk upload for Pool Entries. (OA-49)
- Person summary UI component (OA-64)
- Added age calculation to person summary
- Add a component for displaying patient visits.
- Add Transactional annotation and finalize import template variables.
- Color picker for annotation labels
- Added tests for person summary component (OA-64)
- Prevent editing schemas with related pools
- Visit List component tests.
- Topic Upload interface. (OA-82)
- Validation for uniqueness of inputs.
- Annotation label validation on submit and tests.
- Add validation on pool entry uploads (OA-84)
- Add sort order to pool entry upload. (OA-83)
- Add formatting for visit dates (OA-81)
- Added new environment variables; removed extra fields from Person
- Add reviewer workflow to judgment interface. (OA-73)
- Add tests for Pool Entries component.
- Color picker - assign random color if default in use
- Patient conditions ; also added datatables to visits (OA-67)
- Add field requirements. (OA-96)
- Added an accordion interface to the Person detail page
- Add unique constraints and handle violations (OA-80)
- Bootstrap 5 Alerts (OA-77)
- Add validation for document_id on pool entry form.

### Changed

- Update Configuration (OA-55)
- Clear OMOP Hibernate Dialect Property (OA-55)
- Refactor Entity and Repository Packages (OA-55)
- Replace java.sql.Timestamp with java.util.Date
- Update src/main/java/org/octri/omop_annotator/controller/HomeController.java
- Upgrade to Vue 3 (OA-60)
- Webpack 5 (OA-60)
- Add missing .babelrc (OA-60)
- Upgrade ESLint (OA-60)
- Upgrade Test Infrastructure (OA-60)
- Add Example Test (OA-60)
- Upgrade Prettier (OA-60)
- Move entities under admin menu.
- Edit annotation schema and labels in single form. (OA-61)
- Upgrade to bootstrap 5 (OA-62)
- Move props definition and apply formatter to file.
- Improve interactions with annotation schema editing (OA-61)
- Delete label by index.
- Organize imports.
- Update src/main/resources/frontend/test/components/AnnotationLabels.spec.js
- Check for null patient id
- Use rem unit to size table.
- Make poolComments optional when uploading pool entries.
- Format javascript
- Fixed quoting in javascript literals
- Formatting for specs; added test for person summary data loading
- Await promise resolution in tests.
- Run JS Unit Tests on Commit
- Update JS Test Result Path
- Person summary component refinements; better date handling for front-end display
  (OA-87)
- Reverted property
- Revert change to Repository.
- Break out color logic into methods.
- Switch http status to unprocessable entity.
- Prevent basic users from accessing /admin route.
- HTML ids should use snake case. (OA-89)
- Remove unnecessary id attribute from div.
- Move common scripts to footer.
- Rewrite Pool Entries component to use data tables.
- Prettier formatting.
- Additional cleanup
- Add PR feedback. Extract spinner component.
- Add correct datatables class to upload confirmation pages.
- Add invalid feedback message to Annotation label display.
- Enforce topic narrative requirement.

### Fixed

- Minor Fixes (OA-44)
- Whitespace fixes
- Formatting
- Cleaned up spacing issue
- Fix validation appearance on annotation labels. (OA-78)
- Fix all select fields to use the form-select class.
- Fix template string for viewing pool topics
- Fixed html ids for visit code to use underscores; clarified intent for using
  datatables in Vue components; formatting
- Fix flow for unauthenticated users. (OA-65)
- Fix annotation label validation and console errors

### Removed

- Removed redundant item in menu
- Remove webjar dependency, upgrade node, and remove comments.
- Delete Orphaned Package
- Remove IE From Babel Preset Config (OA-60)
- Remove placeholder Vue component and generated view for Annotation Label.
- Remove nested label in mustache template for User

## [0.1.0] - 2022-02-16

### Added

- Initial Commit
- Vendor-Specific Flyway Migrations (OA-44)

### Changed

- Update Default Branch in Jenkinsfiles (OA-44)
- Trim pom.xml Whitespace (OA-44)

### Fixed

- Fix Stage Deployment MySQL Trust Store (OA-44)

[unreleased]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v2.0.2...HEAD
[2.0.2]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v2.0.1...v2.0.2
[2.0.1]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v2.0.0...v2.0.1
[2.0.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.4.0...v2.0.0
[1.4.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.3.2...v1.4.0
[1.3.2]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.3.1...v1.3.2
[1.3.1]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.3.0...v1.3.1
[1.3.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.0.4...v1.1.0
[1.0.4]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.0.3...v1.0.4
[1.0.3]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.0.2...v1.0.3
[1.0.2]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v1.0.0...v1.0.2
[1.0.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.7.2...v1.0.0
[0.7.2]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.7.1...v0.7.2
[0.7.1]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.7.0...v0.7.1
[0.7.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.6.0...v0.7.0
[0.6.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.5.0...v0.6.0
[0.5.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.4.0...v0.5.0
[0.4.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/OHSU-OCTRI/omop-annotator/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/OHSU-OCTRI/omop-annotator/tree/v0.1.0