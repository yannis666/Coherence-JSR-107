Coherence-JSR-107
==============

About
-----

*Coherence-JSR-107* is an experimental implementation of JSR107 implemented as an adapter.

Copyright
---------

Copyright (c) Oracle

Getting started
---------------
    git clone git@github.com:yannis666/Coherence-JSR-107.git
    git clone git@github.com:jsr107/jsr107spec
    git clone git@github.com:jsr107/jsr107tck
    git clone git@github.com:jsr107/RI

Creating a pom.xml at the root will make development easier. For convenience one is included in Coherence-JSR-107.
    ln -s Coherence-JSR-107/master-pom.xml pom.xml
For some initial setup please follow the instructions in [Coherence-JSR-107/jsr107/README.md](Coherence-JSR-107/jsr107/README.md)

To run the TCK using the Coherence adapter you can use

    mvn \
      -Dimplementation-groupId=oracle \
      -Dimplementation-artifactId=coherence.jsr107 \
      -Dimplementation-version=0.1-SNAPSHOT \
      install

