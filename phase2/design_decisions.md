#Key Design Decisions

##Controllers/Presenters

 The decision to keep Controllers and Presenters together the same class was not an light one. 
However, due to our decision of using Android Studio, we realized that separating them would lead 
to the opposite of good code design. Separating the controller and presenter is done with the 
intention of separating responsibility and making sure the code is more extensible.
However, since the XML files as well as the Listeners all depend on the Activity, 
separating would lead to several major problems:
    (a) Whether or not we made the Activity the presenter or the controller, the new, separated class
    would be far too small and comprised of only one method(which is a code smell)
    (b) Making the Activity the controller and making a new Presenter class would cause coupling 
    (since the xml files depend on the properties of the Activity, we would have to pass in a 
    parameter of "this" into the presenter). This would be very poor SOLID design because it would
    increase dependencies, reduce encapsulation, and even reduce extensibility. If we wanted
    to add more elements or change the way something was displayed/processed, we would have to 
    change methods in both classes and it would completely destroy the purpose of the Presenter class.
    (c) If we made the Activity the presenter instead, this would mean that (i) we would have to
    make many very small Controller classes for each Activity or (ii) we would need to make a couple 
    larger controllers that would be instantiated in every one of our 20+ activities
        (i) This is a code smell (see (a)), and isn't a very good design decision since it
        does brings no benefit. Since each Activity is responsible for a very significant task,
        adding more features and making more decisions with regards to the specific activity in the
        for extensibility is relatively simple already. If we separated and had small controller 
        classes, our best option to maintain good code design would be to create abstract parent 
        classes in order to keep track of methods that are consistently needed across various 
        activities. However, this caused us a dilemma, because there are so many different collections
        of use cases and methods needed for all of our different activities. Implementing interfaces
        is not a solution here, because it would lead to an extremely high amount of duplicated code.
        (ii) Larger controllers: We would be instantiating the controller inside 20+ classes.
         This also means that the controller essentially becomes more of a Facade and we would be
         putting a ton of functionality into every activity's controller that the activity will 
         NEVER use. Since most activities only use around 1-2 use cases, having larger 
         controllers would lead to issues with passing the use cases in from the Activities
    (d) Most importantly, it seems that separating them would make the code more extensible and
    follow SOLID. However, we realized that if we separated the classes and ever wanted to change
    how something was presented, changes would have to be made to both classes either way. It was
    a better idea to just make multiple activities instead of  
    (e) We feel that in this particular situation, since we are using the Android platform for our
    program, division of responsibilities is accomplished by creating many different Activities
    each responsible for a small part of the program instead of having larger Controllers and 
    Presenters.
    
Ultimately, this decision was made after discussion with the professor and by balancing code smells 
and SOLID principles against clean architecture structure. We thought the former two design principles
 were more important than the latter in this case.
 
 #BundleActivity
 We decided to make a BundleActivity abstract class for most of our activities (but not all). It
 handles the bundle of data that is being passed between activities as well as help take care of 
 back button functionality for all the classes. It also has variables for the String keys in the 
 bundle of data. We wanted to make sure that activities 
 Although it is technically possible to access the other use cases, this is due to 
 We wanted to keep the Bundle that was needed
 in every activity due to using intents private, so we made it private in the BundleActivity class.
 Overall, we felt this was a great class to have because it reduced a lot of repeated code throughout
 the program and also increased encapsulation.