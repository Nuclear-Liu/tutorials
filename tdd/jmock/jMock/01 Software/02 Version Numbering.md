# Version Numbering

The jMock project follows strict rules that govern its use of version numbers. 
The version number of a release indicates how that release is compatible with previous and future releases.

Version numbers have the form major.minor.patch. 
The major version number identifies the API version. 
A release that changes the API in a way that breaks backwards compatibility will increment the major version number and reset the minor and patch versions to zero. 
The minor version number identifies the backwards compatible revision of the API. 
An API version that adds new elements to the API will have the same major version, increment the minor version and set the patch version to zero. 
The patch version number identifies revisions that do not change the API. 
A release that fixes bugs or refactors implementation details without changing the API will have the same minor and major versions as the previous release and increment the patch number.

A hypothetical example:

| Version | Description|
| --- | --- |
| `1.0.0` | First release |
| `1.0.1` | Improves Javadoc comments |
| `1.1.0` | Adds new API elements |
| `1.2.0` | Adds new API elements, deprecates some API elements |
| `1.2.1` | Fixes bugs |
| `2.0.0` | Incompatible API changes, removes API elements deprecated by version 1.2.0. |
| `2.1.0` | Adds new API elements |
| `etc.` | etc. |

## Release Candidates

Before a new major or minor release, jMock will make release candidate (RC) packages available so that users can test them against their own code. 
There will be one or more release candidates given the version `major.minor.0.RCn`, where the major and minor version numbers identify the upcoming release and RC1 identifies the first candidate release, RC2 the second, and so on.

A release candidate does not guarantee backward compatability with new API features introduced by any previous RC of the same upcoming version. 
A major version RC can change/remove API features introduced in a previous RC for the same major version; a minor version RC can change API features introduced by any previous RC of the same upcoming minor version but guarantees backward compatability with the previous minor version.

## Development Snapshots

During development, the continuous integration process automatically builds snapshot packages. 
These are not guaranteed to be complete: although the unit tests will all pass the snapshot will probably contain failing acceptance tests that describe planned or requested features that have not yet been implemented. 
Snapshots are identified by the UTC time at which the continuous integration process built the package. 
The timestamp has the form YYYYMMDD-hhmmss, where YYYY is the (four-digit) year, MM the month, DD the day, hh the hour, mm the minute and ss the second. 
The snapshot version is not included in the filename of the snapshot archive itself, but expanding the archive will create a directory named after the snapshot version.

## Versioning and Deprecation

A minor release might deprecate some API features. 
Deprecated features will not actually be removed until the next major release. 
A release will never remove API features that have not been deprecated in a previous release.
