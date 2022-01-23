# Answers to Questions and Exercises: Aggregate Operations


## Questions


* Q: A sequence of aggregate operations is known as a ___ .

  A: Pipeline

* Q: Each pipeline contains zero or more ___ operations.

  A: Intermediate

* Q: Each pipeline ends with a ___ operation.

  A: Terminal

* Q: What kind of operation produces another stream as its output?

  A: Intermediate

* Q: Describe one way in which the `forEach` aggregate operation differs from the enhanced `for` statement or iterators.

  A: The `forEach` aggregate operation lets the system decide "how" the iteration takes place. 
  Using aggregate operations lets you focus on "what" instead of "how."

* Q: True or False: A stream is similar to a collection in that it is a data structure that stores elements.

  A: False. 
  Unlike a collection, a stream is not a data structure. 
  It instead carries values from a source through a pipeline.

* Q: Identify the intermediate and terminal operations in this code:

  ```text
  double average = roster
      .stream()
      .filter(p -> p.getGender() == Person.Sex.MALE)
      .mapToInt(Person::getAge)
      .average()
      .getAsDouble();
  ```

  A: Intermediate: `filter`, `mapToInt`

  Terminal: average

  The terminal operation `average` returns an `OptionalDouble`. 
  The `getAsDouble` method is then invoked on that returned object. 
  It is always a good idea to consult the [API Specification]() for information about whether an operation is intermediate or terminal.

* Q: The code `p -> p.getGender() == Person.Sex.MALE` is an example of what?
  
  A: A lambda expression.

* Q: The code `Person::getAge` is an example of what?
  
  A: A method reference.

* Q: Terminal operations that combine the contents of a stream and return one value are known as what?
  
  A: Reduction operations.

* Q: Name one important difference between the Stream.reduce method and the Stream.collect method.
  
  A: `Stream.reduce` always creates a new value when it processes an element. 
  `Stream.collect` modifies (or mutates) the existing value.

* Q: If you wanted to process a stream of names, extract the male names, and store them in a new `List`, would `Stream.reduce` or `Stream.collect` be the most appropriate operation to use?
  
  A: The collect operation is most appropriate for collecting into a `List`.

  Example:

  ```text
  List<String> namesOfMaleMembersCollect = roster
      .stream()
      .filter(p -> p.getGender() == Person.Sex.MALE)
      .map(p -> p.getName())
      .collect(Collectors.toList());
  ```

* Q: True or False: Aggregate operations make it possible to implement parallelism with non-thread-safe collections.
  
  A: True, provided that you do not modify (mutate) the underlying collection while you are operating on it.

* Q: Streams are always serial unless otherwise specified. 
  How do you request that a stream be processed in parallel?

  A: Obtain the parallel stream by invoking `parallelStream()` instead of `stream()`.


## Exercises


* Exercise: Write the following enhanced `for` statement as a pipeline with lambda expressions. 
  Hint: Use the `filter` intermediate operation and the `forEach` terminal operation. 

  ```text
  for (Person p : roster) {
      if (p.getGender() == Person.Sex.MALE) {
          System.out.println(p.getName());
      }
  }
  ```

  Answer:

  ```text
  roster
      .stream()
      .filter(e -> e.getGender() == Person.Sex.MALE)
      .forEach(e -> System.out.println(e.getName());
  ```

* Convert the following code into a new implementation that uses lambda expressions and aggregate operations instead of nested `for` loops. 
  Hint: Make a pipeline that invokes the `filter`, `sorted`, and `collect` operations, in that order. 

  ```text
  List<Album> favs = new ArrayList<>();
  for (Album a : albums) {
      boolean hasFavorite = false;
      for (Track t : a.tracks) {
          if (t.rating >= 4) {
              hasFavorite = true;
              break;
          }
      }
      if (hasFavorite)
          favs.add(a);
  }
  Collections.sort(favs, new Comparator<Album>() {
                             public int compare(Album a1, Album a2) {
                                 return a1.name.compareTo(a2.name);
                             }});
  ```

  Answer: 

  ```text
  List<Album> sortedFavs =
    albums.stream()
          .filter(a -> a.tracks.anyMatch(t -> (t.rating >= 4)))
          .sorted(Comparator.comparing(a -> a.name))
          .collect(Collectors.toList());
  ```


Here we have used the stream operations to simplify each of the three major steps -- identification of whether any track in an album has a rating of at least 4 (`anyMatch`), the sorting, and the collection of albums matching our criteria into a `List`. 
The `Comparator.comparing()` method takes a function that extracts a `Comparable` sort key, and returns a `Comparator` that compares on that key. 
