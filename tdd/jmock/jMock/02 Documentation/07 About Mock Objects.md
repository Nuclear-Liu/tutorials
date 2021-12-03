Mock Objects

About Mock Objects, a technique for improving the design of code within Test-Driven Development
02 May 2010
Porting the blog

Update Now fixed links to files and images.

Blogger have changed their infrastructure and no longer support sftp hosting, so we've chosen to move the whole thing to their servers (taking some of the load off ours). In the process, we seem to have lost some of the detail, so please bear with us while we put it all back.

In the meantime, don't forget our book :)
Posted by Steve Freeman at 01:00 1 comments
Labels: news
08 September 2009
A Brief History of Mock Objects

As an afterword to the book, we had Tim Mackinnon write up a piece on how we got here. In compensation for having been so quiet for a while, here's that chapter.
Introduction

The ideas and concepts behind mock objects didn't materialise in a single day. There's a long history of experimentation, discussion, and collaboration between many different developers who have taken the seed of an idea and grown it into something more profound. The final result should help you with your software development; but the background story of “The Making of Mock Objects” is also interesting—and a testament to the dedication of the people involved. I hope revisiting this history will inspire you too to challenge your thoughts on what is possible and to experiment with new practices.
Origins

The story began on a roundabout near Archway station in London in late 1999. That evening, several members of a London-based software architecture group met to discuss topical issues in software. The discussion turned to experiences with Agile Software Development and I mentioned the impact that writing tests seemed to be having on our code. This was before the first Extreme Programming book had been published, and teams like ours were still exploring how to do test-driven development—including what constituted a good test. In particular, I had noticed a tendency to add “getter” methods to our objects to facilitate testing. This felt wrong, since it could be seen as violating object-oriented principles, so I was interested in the thoughts of the other members. The conversation was quite lively—mainly centering on the tension between pragmatism in testing and pure object-oriented design. We also had a recent example of a colleague, Oli Bye, stubbing out the Java Servlet API for testing a web application without a server.

I particularly remember from that evening a crude diagram of an onion and its metaphor of the many layers of software, along with the mantra “No Getters! Period!” The discussion revolved around how to safely peel back and test layers of that onion without impacting its design. The solution was to focus on the composition of software components (the group had discussed Brad Cox's ideas on software components many times before). It was an interesting collision of opinions, and the emphasis on composition—now referred to as dependency injection—gave us a technique for eliminating the getters we were “pragmatically” adding to objects so we could write tests for them.

The following day, our small team at Connextra started putting the idea into practice. We removed the getters from sections of our code and used a compositional strategy by adding constructors that took the objects we wanted to test via getters as parameters. At first this felt cumbersome, and our two recent graduate recruits were not convinced. I, however, had a Smalltalk background, so to me the idea of composition and delegation felt right. Enforcing a “no getters” rule seemed like a way to achieve a more object-oriented feeling in the Java language we were using.

We stuck to it for several days and started to see some patterns emerging. More of our conversations were about expecting things to happen between our objects, and we frequently had variables with names like expectedURL and expectedServiceName in our injected objects. On the other hand, when our tests failed we were tired of stepping through in a debugger to see what went wrong. We started adding variables with names like actualURL and actualServiceName to allow the injected test objects to throw exceptions with helpful messages. Printing the expected and actual values side-by-side showed us immediately what the problem was.

Over the course of several weeks we refactored these ideas into a group of classes: ExpectationValue for single values, ExpectationList for multiple values in a particular order, and ExpectationSet for unique values in any order. Later, Tung Mac also added ExpectationCounter for situations where we didn't want to specify explicit values but just count the number of calls. It started to feel as if something interesting was happening, but it seemed so obvious to me that there wasn't really much to describe. One afternoon, Peter Marks decided that we should come up with name for what we were doing—so we could at least package the code—and, after a few suggestions, proposed “mock.” We could use it both as a noun and a verb, and it refactored nicely into our code, so we adopted it.
Spreading the Word

Around this time, we also started the London Extreme Tuesday Club (XTC) to share experiences of Extreme Programming with other teams. During one meeting, I described our refactoring experiments and explained that I felt that it helped our junior developers write better object-oriented code. I finished the story by saying, “But this is such an obvious technique that I'm sure most people do it eventually anyway.” Steve pointed out that the most obvious things aren't always so obvious and are usually difficult to describe. He thought this could make a great paper if we could sort the wood from the trees, so we decided to collaborate with another XTC member (Philip Craig) and write something for the XP2000 conference. If nothing else, we wanted to go to Sardinia.

We began to pick apart the ideas and give them a consistent set of names, studying real code examples to understand the essence of the technique. We backported new concepts we discovered to the original Connextra codebase to validate their effectiveness. This was an exciting time and I recall that it took many late nights to refine our ideas—although we were still struggling to come up with an accurate “elevator pitch” for mock objects. We knew what it felt like when using them to drive great code, but describing this experience to other developers who weren't part of the XTC was still challenging.

The XP2000 paper and the initial mock objects library had a mixed reception—for some it was revolutionary, for others it was unnecessary overhead. In retrospect, the fact that Java didn’t have good reflection when we started meant that many of the steps were manual, or augmented with code generation tools. This turned people off—they couldn't separate the idea from the implementation.
Another Generation

The story continues when Nat Pryce took the ideas and implemented them in Ruby. He exploited Ruby's reflection to write expectations directly into the test as blocks. Influenced by his PhD work on protocols between components, his library changed the emphasis from asserting parameter values to asserting messages sent between objects. Nat then ported his implementation to Java, using the new Proxy type in Java 1.3 and defining expectations with “constraint” objects. When Nat showed us this work, it immediately clicked. He donated his library to the mock objects project and visited the Connextra offices where we worked together to add features that the Connextra developers needed.

With Nat in the office where mock objects were being used constantly, we were driven to use his improvements to provide more descriptive failure messages. We had seen our developers getting bogged down when the reason for a test failure was not obvious enough (later, we observed that this was often a hint that an object had too many responsibilities). Now, constraints allowed us to write tests that were more expressive and provided better failure diagnostics, as the constraint objects could explain what went wrong. For example, a failure on a stringBegins constraint could produce a message like:

Expected a string parameter beginning with "http"
but was called with a value of "ftp.domain.com"

We released the new improved version of Nat's library under the name Dynamock.

As we improved the library, more programmers started using it, which introduced new requirements. We started adding more and more options to the API until, eventually, it became too complicated to maintain—especially as we had to support multiple versions of Java. Meanwhile, Steve tired of the the duplication in the syntax required to set up expectations, so he introduced a version of a Smalltalk cascade—multiple calls to the same object.

Then Steve noticed that in a statically typed language like Java, a cascade could return a chain of interfaces to control when methods are made available to the caller—in effect, we could use types to encode a workflow. Steve also wanted to improve the programming experience by guiding the new generation of IDEs to prompt with the “right” completion options. Over the course of a year, Steve and Nat, with much input from the rest of us, pushed the idea hard to produce jMock, an expressive API over our original Dynamock framework. This was also ported to C# as NMock. At some point in this process, they realized that they were actually writing a language in Java which could be used to write expectations; they wrote this up later in an OOPLSA paper.
Consolidation

Through our experience in Connextra and other companies, and through giving many presentations, we improved our understanding and communication of the ideas of mock objects. Steve (inspired by some of the early lean software material) coined the term “needs-driven development,” and Joe Walnes, another colleague, drew a nice visualisation of islands of objects communicating with each other. Joe also had the insight of using mock objects to drive the design of interfaces between objects. At the time, we were struggling to promote the idea of using mock objects as a design tool; many people (including some authors) saw it only as a technique for speeding up unit tests. Joe cut through all the conceptual barriers with his simple heuristic of “Only mock types you own.”

We took all these ideas and wrote a second conference paper, “Mock Roles not Objects”. Our initial description had focused too much on implementation, whereas the critical idea was that the technique emphasizes the roles that objects play for each other. When developers are using mock objects well, I observe them drawing diagrams of what they want to test, or using CRC cards to roleplay relationships“these then translate nicely into mock objects and tests that drive the required code. Since then, Nat and Steve have reworked jMock to produce jMock2, and Joe has extracted constraints into the Hamcrest library (now adopted by JUnit). There’s also now a wide selection of mock object libraries, in many different languages.

The results have been worth the effort. I think we can finally say that there is now a well-documented and polished technique that helps you write better software. From those humble “no getters” beginnings, this book summarizes years of experience from all of us who have collaborated, and adds Steve and Nat's language expertise and careful attention to detail to produce something that is greater than the sum of its parts.
Posted by Steve Freeman at 10:05 5 comments
Labels: book, growing object-oriented software, history, talk
12 August 2009
Book: The Worst is Over

We've been silent for a while, getting on with writing.

The news is that we're done with copy editing and now it's into the presentation work such as formatting, proofing, and indexing. We're even up on Amazon, although our editor says that we should be out before November.
Posted by Steve Freeman at 09:51 6 comments
Labels: book, growing object-oriented software, news
11 June 2009
Using Mocks and Tests to Design Role-Based Objects

Isaiah Perumalla has had a paper published by MSDN. They trimmed it a bit, so we're hosting the original here.
Posted by Steve Freeman at 21:24 0 comments
Labels: news, publications
21 May 2009
Another update to the book
We've gone very quiet for a while because we've been busy writing (and rewriting, and rewriting). We've reworked some of the example, and done some substantial clean-up in parts III and IV. Let us know on the list what you think.
Posted by Steve Freeman at 22:30 4 comments
Labels: book, growing object-oriented software, news
02 April 2009
Book. More and more
As the deadlines approach… We've published new chapters: another chapter in the example on handling failures, one on our approach to Object-Oriented design and one on how that relates to Mock Objects, and one on testing asynchronous code. Enjoy—and comment.
Posted by Steve Freeman at 22:43 2 comments
Labels: book, growing object-oriented software
27 March 2009
Book. More chapters posted
We've been quiet for a while because we've been trying to make progress with the book. To let you know how we're doing, we've just posted some more draft chapters: there's more from the extended example, one on how to maintain ongoing TDD, and one on how to cope with threading. As always comments welcome, and join the discussion list.
Posted by Steve Freeman at 11:05 0 comments
Labels: book, growing object-oriented software, news
13 December 2008
googlemock

Google have just announced googlemock, their C++ mocking framework. More details at http://code.google.com/p/googlemock/. It looks like they've done a lot of work.
Posted by Steve Freeman at 20:16 1 comments
Labels: mocks in action, news
27 November 2008
Only mock types you own (revisited)

There was a brief discussion on the JMock mailing list recently where we helped out a new user. In the process, we noticed that he was mocking Java Set, which we tend to avoid, because it's not a type that we own—it's not in the domain of the problem we're trying to solve. The way I put it was,

    We see a lot of people using, for example, List<House> when what they actually mean is Street. We try to use types we own that represent concepts in the domain, rather than built-in or library types which we keep for implementing these domain types. If the domain types are defined in terms of roles, then they're often appropriate for mocking.

Isiah followed up with an old post of his own.

I think we have a satisfied customer:

    Thank you Steve and Isiah, I think I just took a large step in the world of TDD.

It's nice when that happens...
Posted by Steve Freeman at 17:07 2 comments
Labels: design, explanation
More example, reworking the process
We've posted some more chapters from the long worked example. We've also been reworking the material on the Process of TDD. Take a look, and comments are always welcome.
Posted by Steve Freeman at 17:03 2 comments
Labels: book, growing object-oriented software 