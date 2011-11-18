Preliminaries
=============

Coherence is not available in the Maven repository, so you will need to add
it to your local Maven repository before you start.

Obtain a copy of the Coherence jar. For example you can download from : [here](http://www.oracle.com/technetwork/middleware/coherence/downloads/index.html)

set COHERENCE_HOME to point at the installation directory of Coherence.

`ant all`

This will result in the coherence jar being placed in your local Maven repository.

From here `mvn install` should work.