PCP Solver is a Java application which (tries to) solve instances of Post's Correspondence Problem.

# Post's Correspondence Problem

*Post's Correspondence Problem*, PCP for short, is mostly of theoretical importance. It is an example of a problem computers cannot solve and goes as follows. You are given a set of dominoes. Each domino has a topstring and a bottomstring. For example, the domino `a/ab` has topstring `a` and bottomstring `ab`. You must determine whether or not there exists a sequence of these dominoes such that the concatenation of the topstrings is the same as the concatenation of the bottomstrings. Such a sequence&mdash;called a 'match'&mdash;must contain at least one domino and may use any of the given dominoes zero or more times.

For example, the set of dominoes {`ab/b`, `b/a`, `a/ab`} has a match. The concatenation of the topstrings in the sequence `a/ab`, `b/a`, `ab/b` is the same as the concatenation of the bottomstrings: `abab`.

![screenshot](https://dcatteeu.github.io/images/pcpsolver.png)

For more information on PCP, see [Wikipedia](http://en.wikipedia.org/wiki/Post_correspondence_problem) and [Ling Zhao's PCP website](http://webdocs.cs.ualberta.ca/~games/PCP/).

*PCP Solver* tries to solve as many PCP instances as possible. It applies an exhaustive [Iterative Deepening A* search](http://en.wikipedia.org/wiki/IDA*) (implemented by the [JSearch](https://github.com/dcatteeu/jsearch) library) to find a match. The search finishes either when a match is found, or when all possibilities are explored and no match exists. Since PCP is unsolvable, for some instances the search will continue indefinitely. PCP Solver also implements some simple rules to detect PCP instances without a match and thereby avoids a long unnecessary search in some cases. For more information on the state of the art of solving PCP instances, see [Ling Zhao's website](http://webdocs.cs.ualberta.ca/~games/PCP).

# Download and run

Download the executable JAR ([v1.1](https://dcatteeu.github.io/downloads/pcpsolver-1.1-jar-with-dependencies.jar), [v1.0](https://dcatteeu.github.io/downloads/pcpsolver-1.0-jar-with-dependencies.jar)) and run by double clicking or execute `java -jar pcpsolver-1.1-jar-with-dependencies.jar` from the commandline.

PCP Solver requires [Java 6](http://java.com/en/download/index.jsp) or later.

# Source code

The [source code](https://github.com/dcatteeu/pcpsolver/) is available on GitHub and is licensed under [GPLv3](http://www.gnu.org/licenses/gpl.html).

# Bugs, issues, suggestions, ...

Please [report](https://github.com/dcatteeu/pcpsolver/issues/new) any bugs or suggestions via GitHub.

# Alternatives

* Ling Zhao provides the [C++ source code](http://webdocs.cs.ualberta.ca/~games/PCP/) of his PCP Solver. You must compile it yourself. It is probably the fastest and most advanced PCP Solver available, but only works for PCP instances with a binary alphabet (`0`s and `1`s). The code is also available at [GitHub](https://github.com/chrozz/PCPSolver).
* There is an [online PCP Solver](http://jamesvanboxtel.com/projects/pcp-solver/) implemented in PHP. It is capable of solving some simple PCP instances, but may crash on harder problems.
* [Post Correspondence Problem](https://www.interviewbit.com/blog/post-correspondence-problem/)
